package vo;

import java.util.Date;
import java.util.List;

public class UserVo {

    private int id;
    private String name;
    private String password;
    private String email;
    private int age;
    private int sex;
    private Date birthday;
    private String remark;
    private int orgId;
    private Date addTime;

    public UserVo() {
    }

    public UserVo(int id, String name, String password, String email, int age, int sex, Date birthday, String remark, int orgId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.remark = remark;
        this.orgId = orgId;
    }

    public UserVo(int id, String name, String password, String email, int age, Date birthday, String remark, int orgId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.birthday = birthday;
        this.remark = remark;
        this.orgId = orgId;
    }

    public UserVo(int id, String name, String password, String email, int age, int sex, Date birthday, String remark, int orgId, Date addTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.remark = remark;
        this.orgId = orgId;
        this.addTime = addTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
}
