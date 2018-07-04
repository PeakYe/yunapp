package pers.yf.spring.jdbc.lib;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;

public class JdbcDao<T> {
    @Autowired
    protected JdbcTemplate jdbcTemplate;


    protected Number insert(final String sql, final Object[] args) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey();
    }

    protected T singleResult(String sql, Object[] args, RowMapper<T> rowMapper) {
        List<T> list = jdbcTemplate.query(sql, args, rowMapper);
        if (list == null || list.isEmpty()) {
            return null;
        }

        if (list.size() > 1) {
            throw new JDBCException("查询总数超过限定数量1");
        }
        return list.get(0);
    }

    public static class JDBCException extends RuntimeException {

        public JDBCException(String msg) {
            super(msg);
        }
    }
}
