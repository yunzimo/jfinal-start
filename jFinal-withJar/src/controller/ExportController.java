package controller;

import Handler.OrgHandler;
import Service.ExportService;
import Service.HideService;
import Service.UserService;
import Util.Compress;
import Util.SeparateList;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.json.FastJson;
import com.jfinal.json.Json;
import com.jfinal.plugin.activerecord.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import vo.Result;
import vo.UserVo;

import javax.naming.Name;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/27 14:20
*
*/
public class ExportController extends Controller {

    HideService hideService=new HideService();
    ExportService exportService=new ExportService();
    UserService userService=new UserService();
    private static Logger logger= LogManager.getLogger(UserController.class);

    public void index(){
        render("exportController");
    }

    public void exportData(){
        String rows = getPara("rows");
        String url = getPara("url");

        List<ExcelExportEntity> entities = new ArrayList<>();
        String[] showList = hideService.getShowList(url);
        exportService.setEntities(rows, entities,showList);

        ExportParams params = new ExportParams("用户表","sheetName", ExcelType.XSSF);
        params.setDictHandler(new OrgHandler());

        //创建文件夹
        File dir = new File("E:\\excel\\");
        if(!dir.exists()){
            dir.mkdirs();
        }
        Workbook workbook=null;
//        如果选中的有数据就直接打印
        if(rows.length()>2){
            //数据转换
            List<UserVo> list = JSONObject.parseArray(rows, UserVo.class);
            logger.debug(JSON.toJSONString(list));

            //集合分割
            List<List<UserVo>> lists = SeparateList.partition(list, 5);

//            导出
            workbook=exportService.writeToExcel(entities, params, lists,url);

        }else {

//            List<Record> exportData = exportService.getExportData(url, showList);
//            ArrayList<Map<String, Object>> list = new ArrayList<>();
//            for(Record data:exportData){
//                list.add(data.getColumns());
//            }
//            List<List<Map<String, Object>>> lists = SeparateList.partition(list, 5);


            List<UserVo> realList=new ArrayList<>();
            int page = Integer.parseInt(getPara("pageNum", "1"));
            int pageSize = Integer.parseInt(getPara("pageSize", "8"));
            String name = getPara("name");
            String pwd = getPara("pwd");
            String sort = getPara("sort");
            String order = getPara("order");
            int orgId = Integer.parseInt(getPara("orgId", "0"));
            userService.getTotal(realList, 0, page, pageSize, name, pwd, sort, order, orgId);

            List<List<UserVo>> lists = SeparateList.partition(realList, 5);
            workbook=exportService.writeToExcel(entities, params, lists,url);
        }
        try {
            assert workbook != null;
            workbook.close();

            String newZipPath="E:\\"+url+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".zip";
            Compress.compress("E:\\excel\\",newZipPath);
            Compress.deleteFile(new File("E:\\excel\\"));

            logger.debug(newZipPath);
            renderFile(new File(newZipPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
