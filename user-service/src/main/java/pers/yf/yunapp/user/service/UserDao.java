package pers.yf.yunapp.user.service;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pers.yf.spring.jdbc.lib.JdbcDao;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao extends JdbcDao<UserInfo> {
    private  RowMapper userinfo = new UserRowMapper();

    public void insert(final UserInfo userInfo) {
        userInfo.setId((Long) super.insert("insert into user(user_name,password) values(?,?)", new Object[]{userInfo.getUserName(), userInfo.getPassword()}));

    }

    public UserInfo selectById(Long id) {
        return  jdbcTemplate.query("select * from user where id=?",new Object[]{id}, new UserRowMapper()).get(0);
    }

    public UserInfo findById(Long id) {
        return super.singleResult("select * from user where id=?", new Object[]{id}, userinfo);
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
