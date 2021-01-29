package controller;

import Service.HideService;
import Service.OrgService;
import Service.UserService;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;


import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vo.Result;
import pojo.User;
import vo.UserData;
import vo.UserVo;

import java.text.SimpleDateFormat;
import java.util.*;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/15 9:37
* @Param null
* @Return
* @Exception
*
*/
public class UserController extends Controller {


    private static Logger logger=LogManager.getLogger(UserController.class);

    Cache userRedis = Redis.use("user");

    UserService userService=new UserService();
    OrgService orgService=new OrgService();
    HideService hideService=new HideService();

    public void index(){
        //setAttr("userList",userService.getUserList());

        render("home.jsp");
    }

    /**
    * @Description 保存需要隐藏的列
    * @Author  ChengShaoFan
    * @Date   2021/1/20 14:55
    * @Param
    * @Return  void
    * @Exception
    *
    */
    public void saveShowList(){
        String[] values = getParaValues("list[]");
        String url = getPara("url");
        logger.debug("value====="+Arrays.toString(values));
        logger.debug("url====="+url);

        //userRedis.set("list",values);  存入redis
        boolean b = hideService.saveShowList(url, values);
        if (b){
            renderJson(new Result("success"));
        }else {
            renderJson(new Result("failed"));
        }
    }

    /**
    * @Description 得到需要显示的列
    * @Author  ChengShaoFan
    * @Date   2021/1/20 14:56
    * @Param
    * @Return  void
    * @Exception
    *
    */
    public void getShowList(){
        //String[] lists= userRedis.get("list"); 通过redis获取列表
        String[] list = hideService.getShowList(getPara("url"));
        renderJson(new Result(list,"success","获取数据成功"));
    }

    /**
    * @Description 监测用户名是否重复
    * @Author  ChengShaoFan
    * @Date   2021/1/18 15:53
    * @Param
    */
    public void checkUserName(){
        String name = getPara("name");
        Integer id = getParaToInt("id", 0);
        logger.info("name:"+name);
        boolean b = userService.checkUserName(name,id);
        if(b){
            renderJson(new Result("success"));
        }else{
            renderJson(new Result("failed"));
        }
    }

    /**
    * @Description 获取用户的列表
    * @Author  ChengShaoFan
    * @Date   2021/1/13 16:22
    * @Param
    * @Return  void
    * @Exception
    *
    */
    public void getUserList(){
        int total=0;
        List<UserVo> realList=new ArrayList<>();
        int page = Integer.parseInt(getPara("page", "1"));
        int rows = Integer.parseInt(getPara("rows", "8"));
        String name = getPara("name");
        String pwd = getPara("pwd");
        String sort = getPara("sort");
        String order = getPara("order");
        int orgId = Integer.parseInt(getPara("orgId", "0"));
        total = userService.getTotal(realList, total, page, rows, name, pwd, sort, order, orgId);
        logger.debug(JSON.toJSONString(realList));
        UserData userData = new UserData(total,realList);
        Result result = new Result();
        result.setData(userData);
        renderJson(result.getData());
    }






    /**
    * @Description 添加用户界面的跳转
    * @Author  ChengShaoFan
    * @Date   2021/1/13 16:26
    * @Param
    * @Return  void返回渲染的add.html的视图
    * @Exception
    *
    */
    public void add(){
        render("add.html");
    }


    /**
    * @Description 保存用户信息
    * @Author  ChengShaoFan
    * @Date   2021/1/13 16:26
    * @Param
    * @Return  void
    * @Exception
    *
    */
    public void save(){
        Result result=null;
        User user = getModel(User.class);

        logger.debug(JSON.toJSONString(user));

        if(user.equals(new User())){
            renderText(JSON.toJSONString(new Result("Failed","添加的用户数据不可为空")));
        }else {
            try {
                //boolean save = user.save();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Record record = new Record().set("id", user.getInt("id"))
                        .set("name", user.getStr("name"))
                        .set("pwd", user.getStr("pwd"))
                        .set("email", user.getStr("email"))
                        .set("age", user.getInt("age"))
                        .set("sex", user.getInt("sex"))
                        .set("birthday", user.getDate("birthday"))
                        .set("remark", user.getStr("remark"))
                        .set("org_id", user.getInt("org_id"))
                        .set("addtime", dateFormat.format(new Date()));
                boolean save = Db.save("user", record);
                if(save){
                    result = new Result("Success", "添加用户成功");
                }else {
                    result= new Result("Failed","添加用户失败");
                }
            }catch (Exception e){
                result = new Result("Failed",e.getMessage());
                e.printStackTrace();
            }
            renderText(JSON.toJSONString(result));
        }
    }


    /**
    * @Description 删除用户信息
    * @Author  ChengShaoFan
    * @Date   2021/1/13 16:27
    * @Param
    * @Return  void
    * @Exception
    *
    */
    public void delete(){

        try {
            int id = Integer.parseInt(getPara("id", "0"));
            boolean delete = userService.delete(id);
            if(delete){
                renderJson(new Result("success","删除"+id+"用户成功"));
            }else {
                renderJson(new Result("failed","删除"+id+"用户失败"));
            }
        }catch (Exception e){
            renderJson(new Result("failed",e.getMessage()));
            e.printStackTrace();
        }

    }

    /**
    * @Description 更新用户信息
    * @Author  ChengShaoFan
    * @Date   2021/1/13 16:27
    * @Param
    * @Return  void
    * @Exception
    *
    */
    public void update(){
        try {
            boolean update = getModel(User.class).update();;
            if(update){
                renderText(JSON.toJSONString(new Result("Success","更新用户成功")));
                //renderJson(new Result("Success","更新用户成功"));
            }else {
                renderText(JSON.toJSONString(new Result("Failed","更新用户失败")));
            }
        }catch (Exception e){
            renderText(JSON.toJSONString(new Result("Failed",e.getMessage())));
            e.printStackTrace();
        }
    }

    public void change(){
        if("true".equals(getPara("isNewRecord"))){
            String name = getPara("name");
            String pwd = getPara("pwd");
            Record user = new Record().set("name", name).set("pwd", pwd);
            Db.save("user",user);
        }else {
            Integer id = getParaToInt("id");
            String name = getPara("name");
            String pwd = getPara("pwd");
            Record user = Db.findById("user", id).set("name", name).set("pwd",pwd);
            Db.update("user",user);
        }
        redirect("/test");
    }
}
