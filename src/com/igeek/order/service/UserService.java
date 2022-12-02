package com.igeek.order.service;

import com.igeek.order.dao.UserDao;
import com.igeek.order.entity.User;
import com.igeek.order.utils.JDBCUtils;

import java.sql.SQLException;

public class UserService {

    private UserDao dao = new UserDao();

    //登陆
    public User login(String username , String address){
        try {
            return dao.selectOne(username, address);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close();
        }
        return null;
    }
}