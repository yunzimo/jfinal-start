package vo;

import pojo.User;

import java.util.List;

public class UserData {
    private int total;
    private List<UserVo> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<UserVo> getRows() {
        return rows;
    }

    public void setRows(List<UserVo> rows) {
        this.rows = rows;
    }

    public UserData() {
    }

    public UserData(int total, List<UserVo> rows) {
        this.total = total;
        this.rows = rows;
    }
}
