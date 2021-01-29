package controller;

import com.jfinal.core.Controller;

/**
 * @author Administrator
 */
public class HelloController extends Controller {
    public void index(){
        renderText("Hello JFinal World");
    }
}
