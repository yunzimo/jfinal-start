package controller;

import com.jfinal.core.Controller;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/15 15:56
*
*/
public class TestController extends Controller {
    public void index(){
        render("test.html");
    }
}
