package vo;

import java.util.Date;

/**
* @Description
* @Author  ChengShaoFan
* @Date   2021/1/27 16:50
*
*/
public class UserTemp {

    private int id;
    private String name;
    private String pwd;
    private String email;
    private int age;
    private int sex;
    private String birthday;
    private String remark;
    private int org_id;
    private String addTime;

    public UserTemp() {
    }

    public UserTemp(int id, String name, String pwd, String email, int age, int sex, String birthday, String remark, int org_id, String addTime) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.remark = remark;
        this.org_id = org_id;
        this.addTime = addTime;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
