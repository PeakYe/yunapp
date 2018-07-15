package pers.yf.spring.jdbc.lib;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pers.yf.spring.jdbc.lib.annotation.Column;
import pers.yf.spring.jdbc.lib.annotation.Id;
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
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            return ps;
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
                String columnName = null;
                Column anon = field.getAnnotation(Column.class);
                if (anon != null) {
                    columnName = anon.value();
                } else {
                    Id anonId = field.getAnnotation(Id.class);
                    if (anonId != null) {
                        columnName = anonId.value();
                    }
                }

                if (columnName != null) {

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
            throw new SimpleJdbcException(e);
        }
    }

    protected <A> A singleResult(String sql, Object[] args, RowMapper<A> rowMapper) {
        List<A> list = jdbcTemplate.query(sql, args, rowMapper);
        if (list == null || list.isEmpty()) {
            return null;
        }

        if (list.size() > 1) {
            throw new SimpleJdbcException("查询总数超过限定数量1");
        }
        return list.get(0);
    }

    protected Object singleResult(String sql, Object[] args) {
        final RepEntity annotation = this.getClass().getAnnotation(RepEntity.class);
        final Class x = annotation.value();

        return singleResult(sql, args, creaetRowMapper(x));
    }

    protected <A> A singleResult(String sql, Object[] args, Class<A> clazz) {
        return singleResult(sql, args, creaetRowMapper(clazz));
    }

    private <A> RowMapper<A> creaetRowMapper(final Class<A> clazz) {
        return new RowMapper<A>() {
            @Override
            public A mapRow(ResultSet resultSet, int i) throws SQLException {
                try {
                    A t = clazz.newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        String columnName = null;
                        Column anon = field.getAnnotation(Column.class);
                        if (anon != null) {
                            columnName = anon.value();
                        } else {
                            Id anonId = field.getAnnotation(Id.class);
                            if (anonId != null) {
                                columnName = anonId.value();
                            }
                        }

                        if (columnName != null) {
                            String colName = anon.value();
                            Object val = resultSet.getObject(colName);
                            field.setAccessible(true);
                            field.set(t, val);
                        }

                    }
                    return t;
                } catch (Exception e) {
                    throw new SimpleJdbcException(e);
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

    protected void updateNotNullWithKey(Object object){
        Field[] fields = object.getClass().getDeclaredFields();

        StringBuilder sql = new StringBuilder();
        StringBuilder sqlWhere = new StringBuilder().append(" where ");
        Table table = object.getClass().getAnnotation(Table.class);
        sql.append("update ").append(table.value()).append(" set ");

        List<Object> params = new LinkedList<>();
        for (Field field : fields) {
            String columnName = null;
            Column anon = field.getAnnotation(Column.class);
            if (anon != null) {
                columnName = anon.value();
                if (columnName != null ) {
                    Object fvalue=null;
                    try {
                        fvalue=field.get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (fvalue != null) {
                        sql.append(columnName).append("=?,");
                        params.add(fvalue);
                    }
                }
            } else {
                Id anonId = field.getAnnotation(Id.class);
                if (anonId != null) {
                    columnName = anonId.value();
                    if(columnName==null){
                        throw new SimpleJdbcException("主键不能为空");
                    }
                    sqlWhere.append(columnName).append("=? and ");
                }

            }
        }
        if (sqlWhere.length() < 6) {
            throw new SimpleJdbcException("缺少主键信息");
        }
        sql.deleteCharAt(sql.length() - 1);
        sqlWhere.delete(sqlWhere.length() - 5, sqlWhere.length() - 1);
        sql.append(" ").append(sqlWhere);
        int  i=this.jdbcTemplate.update(sql.toString(), params.toArray());
        if (i != 1) {
            throw new SimpleJdbcException("更新数据错误，行数：" + i);
        }

    }


    public int update(String sql,Object... params){
        return  jdbcTemplate.update(sql, params);
    }

    public <A> A singleResult(Class<A> clazz, Object... params) {
        StringBuilder sql = new StringBuilder();
        Table table = clazz.getAnnotation(Table.class);
        sql.append("select * from ").append(table.value()).append(" where ");
        int ids = 0;
        for (Field field : clazz.getFields()) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                sql.append(id.value()).append("=? and ");
                ids++;
            }
        }
        if (ids != params.length) {
            throw new SimpleJdbcException("主键参数和Id数量不一致");
        }
        sql.delete(sql.length() - 5, sql.length() - 1);
        return  this.singleResult(sql.toString(), params, clazz);
    }

}
