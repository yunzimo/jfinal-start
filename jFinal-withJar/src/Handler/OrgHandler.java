package Handler;

import Service.OrgService;
import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import pojo.Org;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/27 14:04
*
*/
public class OrgHandler implements IExcelDictHandler {

    OrgService orgService=new OrgService();
    @Override
    public String toName(String dict, Object obj, String name, Object value) {

        if("orgId".equals(dict)){
            List<Org> orgList = orgService.getOrgList();
            HashMap<Integer, String> orgs = new HashMap<>(orgList.size());
            for(Org org:orgList){
                orgs.put(org.getInt("id"),org.getStr("name"));
            }
            return orgs.get((Integer) value);
        }
        return null;
    }

    @Override
    public String toValue(String s, Object o, String s1, Object o1) {
        return null;
    }
}
