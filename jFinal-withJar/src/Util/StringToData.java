package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/20 20:20
* @Param null
* @Return
* @Exception
*
*/
public class StringToData {
    /**
    * @Description 字符串转时间
    * @Author  ChengShaoFan
    * @Date   2021/1/20 20:20
    * @Param data
    * @Return  java.util.Date
    * @Exception
    *
    */
    public static Date stringToData(String data){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(data);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
