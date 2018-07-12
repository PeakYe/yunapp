package pers.yf.spring.jdbc.lib;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pers.yf.spring.jdbc.lib.annotation.Column;
import pers.yf.spring.jdbc.lib.annotation.RepEntity;
import pers.yf.spring.jdbc.lib.annotation.Table;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SimpleJdbc {
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

    protected Number insert(Object object) {
        StringBuilder sql1 = new StringBuilder();
        StringBuilder sql2 = new StringBuilder();
        List<Object> params = new LinkedList<>();
        Table table = object.getClass().getAnnotation(Table.class);
        sql1.append(" insert into ").append(table.value()).append("(");
        sql2.append(") values(");

        try {
            //Object t = object.getClass().newInstance();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                Column anon = field.getAnnotation(Column.class);
                if (anon != null) {
                    field.setAccessible(true);
                    Object val = field.get(object);
                    if (val != null) {
                        sql1.append(anon.value()).append(",");
                        sql2.append("?,");
                        params.add(field.get(object));
                    }
                }
            }
            sql1.deleteCharAt(sql1.length() - 1);
            sql2.deleteCharAt(sql2.length() - 1).append(")");
            sql1.append(sql2);
            return insert(sql1.toString(), params.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Object singleResult(String sql, Object[] args, RowMapper<Object> rowMapper) {
        List<Object> list = jdbcTemplate.query(sql, args, rowMapper);
        if (list == null || list.isEmpty()) {
            return null;
        }

        if (list.size() > 1) {
            throw new JDBCException("查询总数超过限定数量1");
        }
        return list.get(0);
    }

    protected Object singleResult(String sql, Object[] args) {
        final RepEntity annotation = this.getClass().getAnnotation(RepEntity.class);
        final Class x = annotation.value();

        return singleResult(sql, args, creaetRowMapper(x));
    }

    protected Object singleResult(String sql, Object[] args, Class clazz) {
        return singleResult(sql, args, creaetRowMapper(clazz));
    }

    private RowMapper creaetRowMapper(final Class clazz) {
        return new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                try {
                    Object t = clazz.newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        Column anon = field.getAnnotation(Column.class);
                        if (anon != null) {
                            String colName = anon.value();
                            Object val = resultSet.getObject(colName);
                            field.setAccessible(true);
                            field.set(t, val);
                        }
                    }
                    return t;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    protected List<?> list(String sql, Object[] args, final Class clazz) {
        return jdbcTemplate.query(sql, args, creaetRowMapper(clazz));
    }

    protected List<?> list(final Class clazz, String sql, Object... args) {
        return jdbcTemplate.query(sql, args, creaetRowMapper(clazz));
    }

    protected int delete(String sql, Object... paramters) {
        return jdbcTemplate.update(sql, paramters);
    }

}
