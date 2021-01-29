package Util;
/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/15 9:39
*/
public class StringToNumber {
    public static int checkAndBack(String str){
        if(str!=null) {
            return Integer.parseInt(str);
        } else {
            return 0;
        }
    }
}
