package controller;

import Service.OrgService;
import com.jfinal.core.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pojo.Org;
import vo.OrgVo;
import vo.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yunzimo
 */
public class OrgController extends Controller {

    private static Logger logger= LogManager.getLogger(UserController.class);

    OrgService orgService=new OrgService();

    public void index(){
        OrgVo treeInfo = getTreeInfo();
        renderJson(treeInfo);
    }


    /**
    * @Description 获得所有部门信息，并以ztree要求的格式
    * @Author  ChengShaoFan
    * @Date   2021/1/21 16:58
    * @Param
    * @Return  void
    * @Exception
    *
    */
    public void getTree(){
        OrgVo treeInfo = getTreeInfo();
        renderJson(treeInfo);
    }

    public void getOrgList(){
        List<Org> orgList = orgService.getOrgList();
        List<OrgVo> orgVos=new ArrayList<>();
        for(Org org:orgList){
            orgVos.add(
                    new OrgVo(
                            org.getInt("id"),
                            org.getStr("name")
                    ));
        }
        renderJson(orgVos);
    }


    /**
    * @Description 查询根节点并递归查询子节点
    * @Author  ChengShaoFan
    * @Date   2021/1/21 16:57
    * @Param
    * @Return  vo.OrgVo
    * @Exception
    *
    */
    public OrgVo getTreeInfo(){
        List<Org> orgs = Org.DAO.find("select * from org where pid = 0");
        Org org = orgs.get(0);
        logger.debug("根节点org="+org);
        OrgVo orgVo=new OrgVo(
                org.getInt("id"),
                org.getStr("name"),
                org.getStr("url"),
                org.getStr("remark"),
                org.getInt("pid")
        );
        orgVo.setOpen(true);
        return getChildren(orgVo);
    }

    /**
    * @Description 查询子节点
    * @Author  ChengShaoFan
    * @Date   2021/1/21 16:58
    * @Param orgVo
    * @Return  vo.OrgVo
    * @Exception
    *
    */
    public OrgVo getChildren(OrgVo orgVo){
        List<Org> childless = Org.DAO.find("select * from org where pid = ?", orgVo.getId());
        if(childless.size()>0){
            List<OrgVo> realChildren=new ArrayList<>();
            for(Org org:childless){
                realChildren.add(new OrgVo(
                        org.getInt("id"),
                        org.getStr("name"),
                        org.getStr("url"),
                        org.getStr("remark"),
                        org.getInt("pid")
                ));
            }
            orgVo.setChildren(realChildren);
            for(OrgVo orgVo1:realChildren){
                getChildren(orgVo1);
            }
        }
        return orgVo;
    }
}
