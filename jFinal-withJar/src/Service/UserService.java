package Service;


import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pojo.User;
import vo.UserVo;

import java.util.List;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/15 9:39
*
*/
public class UserService{
    private static Logger logger= LogManager.getLogger(UserService.class);
    OrgService orgService=new OrgService();

    public List<User> getUserList(){
        return User.DAO.find("select * from user");
    }

    public boolean delete(int id){
        return User.DAO.deleteById(id);
    }

    public User findById(int id){
        return User.DAO.findById(id);
    }

    /**
    * @Description 分页+模糊查询用户信息
    * @Author  chengshaofan
    * @Date   2021/1/13 16:21
    * @Param pageNum 当前页面
    * @Param pageSize 页面大小
    * @Param name 模糊查询的用户姓名
    * @Param pwd 模糊查询的用户密码
    * @Return  com.jfinal.plugin.activerecord.Page<pojo.User>
    * @Exception
    *
    */
    public Page<User> paginate(int pageNum, int pageSize,String name,String pwd,String sort,String order,int orgId,String columns) {
        String sql="from user where 1=1 ";
        if(orgId!=0){
            sql+=" and org_id = "+orgId;
        }
        if(name!=null&&!"".equals(name)){
            sql+=" and name like '%"+name+"%'";
        }
        if(pwd!=null&&!"".equals(pwd)){
            sql+=" and pwd like '%"+pwd+"%'";
        }
        if(sort!=null&&!"".equals(sort)){
            sql+=" order by "+sort+" "+order;
        }
        logger.debug("sql语句=="+sql);
        String select="select "+columns;
        return User.DAO.paginate(pageNum,pageSize,select,sql);
    }



    public boolean checkUserName(String username,Integer id){
        List<User> users = User.DAO.find("select * from user where name = ?", username);
        User user1 = User.DAO.findById(id);
        for(User user:users){
            if(user.equals(user1)) {
                return false;
            }
        }
        logger.info(users);

        return users.size() > 0;
    }

    /**
     * @Description 获得孩子的ID集合
     * @Author  ChengShaoFan
     * @Date   2021/1/21 16:52
     * @Param orgId
     * @Return  java.util.List<java.lang.Integer>
     * @Exception
     *
     */
    public List<Integer> getChild(int orgId){
        return orgService.getChild(orgId);
    }

    /**
     * @Description 递归查询所有的列表
     * @Author  ChengShaoFan
     * @Date   2021/1/21 16:53
     * @Param realList
     * @Param total 所有列表数
     * @Param orgId 查询orgId下的所有成员
     * @Exception
     *
     */
    public int getTotal(List<UserVo> realList, int total, int page, int rows, String name, String pwd, String sort, String order, int orgId) {
        Page<User> userPage = paginate(page,rows,name,pwd,sort,order,orgId,"*");
        List<User> userList = userPage.getList();
        for(User user:userList){
            realList.add(new UserVo(
                    user.getInt("id"),
                    user.getStr("name"),
                    user.getStr("pwd"),
                    user.getStr("email"),
                    user.getInt("age"),
                    user.getInt("sex")==null?1:user.getInt("sex"),
                    user.getDate("birthday"),
                    user.getStr("remark"),
                    user.getInt("org_id"),
                    user.getDate("addtime")==null?null:user.getDate("addtime")
            ));

        }
        total+=userPage.getTotalRow();
        if(orgId!=0){
            if(getChild(orgId).size()>0){
                for(Integer id:getChild(orgId)){
                    total+=getTotal(realList,total,page,rows,name,pwd,sort,order,id);
                }
            }
        }
        return total;
    }




}
