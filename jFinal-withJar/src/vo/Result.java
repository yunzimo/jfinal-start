package vo;
/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/15 9:39
* @Param null
* @Return  
* @Exception   
* 
*/
public class Result {
    private Object data;
    private String code;
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result() {
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(Object data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(String code) {
        this.code = code;
    }
}
