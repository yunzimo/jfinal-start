package vo;

import java.util.List;

/**
 * @author yunzimo
 */
public class OrgVo {
    private int id;
    private String name;
    private String url;
    private String remark;
    private int pid;
    private boolean open;

    private List<OrgVo> children;

    public OrgVo() {
    }

    public OrgVo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public OrgVo(int id, String name, String url, String remark, int pid) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.remark = remark;
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<OrgVo> getChildren() {
        return children;
    }

    public void setChildren(List<OrgVo> children) {
        this.children = children;
    }
}
