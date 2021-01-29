package Service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import controller.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/27 14:40
*/
public class ExportService{

    private static Logger logger= LogManager.getLogger(UserController.class);

    public List<Record> getExportData(String url, String[] showList){
        StringBuilder column= new StringBuilder();
        for(String list:showList){
            if("password".equals(list)){
                column.append("pwd").append(",");
            }else if("orgId".equals(list)){
                column.append("org_id").append(",");
            }else if("operate".equals(list)){
                logger.debug("不用查");
            }else {
                column.append(list).append(",");
            }
        }
        column.deleteCharAt(column.lastIndexOf(","));
        logger.debug("column参数："+column);
        String sql="select "+column+" from "+url;
        logger.debug("拼接完成后的sql语句为："+sql);

        List<Record> records = Db.find(sql);
        return records;
    }

    public void setEntities(String rows, List<ExcelExportEntity> entities, String[] showList) {

        for(String column:showList){
            if("name".equals(column)){
                entities.add(new ExcelExportEntity("姓名","name"));
            }else if("id".equals(column)){
                entities.add(new ExcelExportEntity("ID","id"));
            }else if("password".equals(column)){
                ExcelExportEntity entity=new ExcelExportEntity("密码", "password");
                entities.add(entity);
            }else if("email".equals(column)){
                entities.add(new ExcelExportEntity("邮箱","email"));
            }else if("orgId".equals(column)){
                ExcelExportEntity entity=new ExcelExportEntity("部门", "orgId");
                entity.setDict("orgId");
                entities.add(entity);
            }else if("age".equals(column)){
                entities.add(new ExcelExportEntity("年龄","age"));
            }else if("sex".equals(column)){
                ExcelExportEntity entity = new ExcelExportEntity("性别", "sex");
                entity.setReplace(new String[]{"男_1","女_0"});
                entities.add(entity);
            }else if("birthday".equals(column)){
                ExcelExportEntity entity = new ExcelExportEntity("生日", "birthday");
                entity.setFormat("yyyy-MM-dd");
                entities.add(entity);
            }else if ("addtime".equals(column)){
                ExcelExportEntity entity = new ExcelExportEntity("添加日期", "addtime");
                entity.setFormat("yyyy-MM-dd");
                entities.add(entity);
            }
        }
    }

    public Workbook writeToExcel(List<ExcelExportEntity> entities, ExportParams params, List lists,String url) {
        Workbook workbook=null;
        int i=0;
        for(Object list1:lists){
            i+=1;
            workbook = ExcelExportUtil.exportExcel(params, entities, (List)list1);
            try {
                String path="E:\\excel\\"+url+new SimpleDateFormat("yyyyMMdd").format(new Date())+"_"+i+".xlsx";
                logger.debug(path);
                FileOutputStream os = new FileOutputStream(new File(path));
                workbook.write(os);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return workbook;
    }


}
