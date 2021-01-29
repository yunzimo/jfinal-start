package com.yunzimo.test;


import com.yunzimo.pojo.Person;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author csf
 */
public class TestStart {
    public static final String INDEX_NAME ="hello";
    public static void main(String[] args) {
        JestHello jestHello = new JestHello();

        //添加
        //Person person = new Person(4,"小绿", 22, Arrays.asList("java", "spring", "elasticsearch"));
        //jestHello.addOne(person,INDEX_NAME,"test", String.valueOf(person.getId()));

        //获取映射
        //System.out.println(jestHello.getMappingResult(INDEX_NAME, "person").getJsonString());

        //查询文档
        //String search="{\"query\":{\"match_all\":{}}}";
        //jestHello.searchByJson(search);

        //通过ID删除文档
        //jestHello.deleteById(INDEX_NAME,"test","1");

        //查询
        //jestHello.queryAll(INDEX_NAME);

        //关键字查询
        //jestHello.queryByKey(INDEX_NAME,"skills","语文");

        //多条件查询

        jestHello.queryByMoreKey(INDEX_NAME);
    }

}
