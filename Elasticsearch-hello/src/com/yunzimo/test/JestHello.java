package com.yunzimo.test;

import com.alibaba.fastjson.JSON;
import com.yunzimo.pojo.Person;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.type.TypeExist;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.Set;

/**
 * @author csf
 */
public class JestHello {
    private static final JestClient JEST_CLIENT;

    static{
        JestClientFactory jestClientFactory = new JestClientFactory();
        jestClientFactory.setHttpClientConfig(
                new HttpClientConfig.Builder("http://127.0.0.1:9200").multiThreaded(true).build()
        );
        JEST_CLIENT=jestClientFactory.getObject();
    }
    /**
    * @Description 创建索引
    * @Author  ChengShaoFan
    * @Date   2021/1/25 9:04
    * @Param indexName
    * @Return  void
    * @Exception
    *
    */
    public void createIndex(String indexName){
        boolean indexExists=false;
        try{
            indexExists=JEST_CLIENT.execute(new IndicesExists.Builder(indexName).build()).isSucceeded();
            System.out.println("IndicesExists的结果"+indexExists);
            if(indexExists){
                JEST_CLIENT.execute(new DeleteIndex.Builder(indexName).build());
            }
            JestResult result = JEST_CLIENT.execute(new CreateIndex.Builder(indexName).build());
            if(result.isSucceeded()){
                System.out.println("创建索引成功");
            }else{
                System.out.println("创建索引失败"+result.getErrorMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * @Description 删除索引
    * @Author  ChengShaoFan
    * @Date   2021/1/25 9:25
    * @Param indexName
    * @Return  void
    * @Exception
    *
    */
    public void deleteIndex(String indexName){
        JestResult result=null;
        try {
            result = JEST_CLIENT.execute(new DeleteIndex.Builder(indexName).build());
            if(result.isSucceeded()){
                System.out.println("删除索引成功");
            }else {
                System.out.println("删除失败原因"+result.getErrorMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * @Description 添加文档
    * @Author  ChengShaoFan
    * @Date   2021/1/25 11:26
    * @Param person
    * @Param indexName
    * @Param type
    * @Param id
    * @Return  void
    * @Exception
    *
    */
    public void addOne(Person person,String indexName,String type,String id){
        try {
            DocumentResult result = JEST_CLIENT.execute(new Index.Builder(person).id(id).type(type).index(indexName).build());
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(String indexName,String type,String id){
        try {
            DocumentResult result = JEST_CLIENT.execute(new Delete.Builder(id).index(indexName).type(type).build());
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
    * @Description 查询所有
    * @Author  ChengShaoFan
    * @Date   2021/1/25 14:38
    * @Param index
    * @Return  void
    * @Exception
    *
    */
    public void queryAll(String index){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(index).build();
        try {
            SearchResult result = JEST_CLIENT.execute(search);
            System.out.println(result.getJsonString());
            System.out.println("本次查询共查到"+result.getTotal()+"个关键字");
//            List<SearchResult.Hit<Person, Void>> hits = result.getHits(Person.class);
//            System.out.println(hits);
//            System.out.println(JSON.toJSONString(hits));
//            List<Person> list = result.getSourceAsObjectList(Person.class);
//            System.out.println(JSON.toJSONString(list));
            List<String> source = result.getSourceAsStringList();

            System.out.println("-----"+source);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * @Description 单个查询
    * @Author  ChengShaoFan
    * @Date   2021/1/25 14:43
    * @Param index
    * @Param key
    * @Param value
    * @Return  void
    * @Exception
    *
    */
    public void queryByKey(String index,String key,String value){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(key,value));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(index).build();
        try {
            SearchResult result = JEST_CLIENT.execute(search);
            List<String> source = result.getSourceAsStringList();
            System.out.println(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queryByMoreKey(String index){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(
          QueryBuilders.boolQuery().must(QueryBuilders.termQuery("name","小"))
          .must(QueryBuilders.matchQuery("skills","语文"))
        );
        Search search = new Search.Builder(searchSourceBuilder.toString()).build();
        try {
            SearchResult result = JEST_CLIENT.execute(search);
            System.out.println(result.getJsonString());
            List<String> source = result.getSourceAsStringList();
            System.out.println(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
    * @Description 获得相应索引类型的mapping
    * @Author  ChengShaoFan
    * @Date   2021/1/25 10:30
    * @Param index
    * @Param type
    * @Return  io.searchbox.client.JestResult
    * @Exception
    *
    */
    public JestResult getMappingResult(String index,String type){
        try {

            return JEST_CLIENT.execute(new GetMapping.Builder().addIndex(index).addType(type).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * @Description 通过Json格式的查询条件来搜索
    * @Author  ChengShaoFan
    * @Date   2021/1/25 11:25
    * @Param searchJson
    * @Return  void
    * @Exception
    *
    */
    public void searchByJson(String searchJson){
        try {
            SearchResult result = JEST_CLIENT.execute(new Search.Builder(searchJson).build());
            assert result!=null;
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
