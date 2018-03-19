package com.turtlebot.operation.service.user;

import com.turtlebot.operation.dataobject.User;

import java.util.List;

public interface UserService {

    public boolean addUser(Integer id);
    public User getUser(Integer id);
    public boolean deleteUser(Integer id);
    public List<User> getUserList();
}
