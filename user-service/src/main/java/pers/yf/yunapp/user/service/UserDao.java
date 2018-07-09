package pers.yf.yunapp.user.service;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pers.yf.spring.jdbc.lib.SimpleJdbc;
import pers.yf.spring.jdbc.lib.annotation.RepEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RepEntity(UserInfo.class)
public class UserDao extends SimpleJdbc {
    private  RowMapper userRowMapper = new UserRowMapper();

    public void insert(final UserInfo userInfo) {
        userInfo.setId((Long) super.insert("insert into user(user_name,password) values(?,?)", new Object[]{userInfo.getUserName(), userInfo.getPassword()}));

    }

    public UserInfo selectById(Long id) {
        //return  jdbcTemplate.query("select * from user where id=?",new Object[]{id}, new UserRowMapper()).get(0);
        return (UserInfo) singleResult("select * from user where id=?",new Object[]{id});
    }

    public UserInfo findById(Long id) {
        //return (UserInfo) super.singleResult("select * from user where id=?", new Object[]{id}, userRowMapper);
        return (UserInfo) super.singleResult("select * from user where id=?", new Object[]{id}, UserInfo.class);
    }

    public List<UserInfo> getAllUser() {
        return (List<UserInfo>) super.list("select * from user ", null, UserInfo.class);
    }

    class UserRowMapper implements RowMapper<UserInfo> {
        @Override
        public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserInfo userinfo = new UserInfo();
            userinfo.setId(rs.getLong("id"));
            userinfo.setUserName(rs.getString("user_name"));
            userinfo.setPassword(rs.getString("password"));
            return userinfo;
        }
    }

}
