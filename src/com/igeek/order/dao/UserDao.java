package com.igeek.order.dao;

import com.igeek.order.entity.User;

import java.sql.SQLException;

public class UserDao extends BaseDao<User> {

    public User selectOne(String username,String address) throws SQLException {
        User user = this.getBean("select * from user where username = ? and address = ?",
                User.class, username, address);
        return user;
    }

}