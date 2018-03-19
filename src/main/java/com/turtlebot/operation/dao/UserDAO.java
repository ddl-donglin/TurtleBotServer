package com.turtlebot.operation.dao;

import com.turtlebot.operation.dataobject.User;

import java.util.List;

public interface UserDAO {

    public boolean addUser(Integer id);
    public User getUser(Integer id);
    public boolean deleteUser(Integer id);
    public List<User> getUserList();
}
