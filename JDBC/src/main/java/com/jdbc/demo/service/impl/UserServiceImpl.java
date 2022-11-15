package com.jdbc.demo.service.impl;

import com.jdbc.demo.entity.User;
import com.jdbc.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private static String INSERT_USER_QUERY="INSERT INTO user (id,first_name,last_name)values(?,?,?)";
    private static String UPDATE_USER_BY_ID_QUERY="UPDATE user SET first_name=? WHERE id=?";
    private static String GET_USE_BY_ID_QUERY="SELECT * FROM user WHERE id=?";
    private static String DELETE_USER_BY_ID_QUERY="DELETE FROM user WHERE ID=?";
    private static String GET_USERS_QUERY="SELECT * FROM user";
    private static String GET_JOIN_QUERY ="SELECT p.p_id,p.name,p.address_id,a.a_id,a.street FROM person p,address a";
    private static String INNER_JOIN="SELECT p.p_id,\n" +
            "p.name,\n" +
            "p.address_id,\n" +
            "a.a_id,\n" +
            "a.street  FROM person p\n" +
            "inner join address a on a.a_id=p.address_id";
    private static String RIGHT_JOIN="SELECT name,street from person p right join address a on p.address_id=a.a_id";
    private static String LEFT_JOIN="SELECT name,street from person p left join address a on p.address_id=a.a_id";
    private static String INSERT_MULTIPLE_TABLE="INSERT INTO person(p_id,name)values(?,?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public User saveUser(User user) {
        jdbcTemplate.update(INSERT_USER_QUERY,user.getId(),user.getFirst_name(),user.getLast_name());
        return user;
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY,user.getFirst_name(),user.getId());
        return user;
    }

    @Override
    public User getUser(Integer id) {
        return jdbcTemplate.queryForObject(GET_USE_BY_ID_QUERY,(rs, rowNum) -> {
          return new User(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"));
        },id);
    }

    @Override
    public String deleteById(Integer id) {
        jdbcTemplate.update(DELETE_USER_BY_ID_QUERY,id);
        return "User deleted";
    }


    @Override
    public List<User> allUser() {
        return jdbcTemplate.query(GET_USERS_QUERY, (rs, rowNum) -> {
            return new User(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"));
        });
    }

    public User getUsers(User user) {
        return jdbcTemplate.queryForObject(GET_USE_BY_ID_QUERY,(rs, rowNum) -> {
            return new User(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"));
        },user.getId());
    }
    public String delete(User user) {
        jdbcTemplate.update(DELETE_USER_BY_ID_QUERY,user.getId());
        return "User deleted";
    }

    @Override
    public List<User> allRecord() {
        List<User>userList;
        userList=jdbcTemplate.query(LEFT_JOIN,new Object[]{},BeanPropertyRowMapper.newInstance(User.class));
        return userList;
    }

    @Override
    public int batchUpdate(List<User> userList) {
        jdbcTemplate.batchUpdate(UPDATE_USER_BY_ID_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1,userList.get(i).getFirst_name());
            ps.setInt(2,userList.get(i).getId());

            }

            @Override
            public int getBatchSize() {
                return userList.size();
            }
        });
        return userList.size();
    }

    @Override
    public int multiInsert(List<User> userList) {
        String builder = "INSERT INTO person(p_id,name)values(?,?)" +
                "INSERT INTO address(a_id,street)values(?,?)";
        jdbcTemplate.batchUpdate(builder, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user=userList.get(i);
                ps.setObject(1,user.getP_id());
                ps.setObject(2,user.getName());
                ps.setObject(3,user.getA_id());
                ps.setObject(4,user.getStreet());
            }

            @Override
            public int getBatchSize() {
                return userList.size();
            }
        });
        return 0;
    }

    @Override
    @Transactional
    public User addMultipleUser(User user) {
        String sql="INSERT INTO person(p_id,name)values(?,?)";
        jdbcTemplate.update(sql,user.getP_id(),user.getName());
        String sql1="INSERT INTO address(a_id,street)values(?,?)";
        jdbcTemplate.update(sql1,user.getA_id(),user.getStreet());
        return user;

    }

    @Override
    public int addMultiRecords(List<User> userList) {
        List<Object[]> list=new ArrayList<>();
        for (User user: userList) {
         Object[] objects={user.getId(),
                 user.getFirst_name(),
                 user.getLast_name()};
         list.add(objects);
        }
            jdbcTemplate.batchUpdate(INSERT_USER_QUERY, list);
        return userList.size();
    }

}
