package Service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import controller.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pojo.Hide;

import java.util.Arrays;
import java.util.List;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/27 9:40
*
*/
public class HideService{
    private static Logger logger= LogManager.getLogger(UserController.class);

    public boolean saveShowList(String url, String[] columns){

        //先把所有状态置0
        int update = Db.update("update hide set checked_col = 0 where view_name = ?", url);
        if(update<=0){
            return false;
        }
        //依次更新状态为1
        for(String column:columns){
            List<Record> records = Db.find("select * from hide where view_name = ? and field = ?", url, column);
            if(records.size()>0){
                Record record = records.get(0);
                record.set("checked_col",1);
                boolean b = Db.update("hide", record);
                if(!b){
                    return false;
                }
            }
        }
        return true;
    }

    /**
    * @Description 获取显示列表
    * @Author  ChengShaoFan
    * @Date   2021/1/27 10:25
    * @Param url
    * @Return  java.lang.String[]
    * @Exception
    *
    */
    public String[] getShowList(String url){
        List<Record> list = Db.find("select field from hide where checked_col = 1 and view_name = ? order by order_num",url);

        String[] columns=new String[list.size()];
        for(int i=0;i<list.size();i++){
            columns[i]= list.get(i).get("field");
        }
        logger.debug("转化后的columns"+ Arrays.toString(columns));
        return columns;
    }


}
