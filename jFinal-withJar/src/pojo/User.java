package pojo;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> {


    private static final long serialVersionUID = 6794387085642687120L;

    public final static User DAO = new User();

}
