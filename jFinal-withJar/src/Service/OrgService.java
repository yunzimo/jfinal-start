package Service;

import pojo.Org;
import pojo.User;

import java.util.ArrayList;
import java.util.List;

public class OrgService{

    public List<Integer> getChild(int orgId){
        List<Org> orgs = Org.DAO.find("select * from org where pid = ?", orgId);
        ArrayList<Integer> children = new ArrayList<>();
        if(orgs.size()>0){
            for(Org org:orgs){
                children.add(org.getInt("id"));
            }
        }
        return children;
    }
    public List<Org> getOrgList(){
        return Org.DAO.find("select * from org");
    }
}
