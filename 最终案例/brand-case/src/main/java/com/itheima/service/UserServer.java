package com.itheima.service;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserServer {

    User Login(String username,String password);

    boolean register(User user);
}
