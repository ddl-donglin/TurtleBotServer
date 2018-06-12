package com.turtlebot.operation.service.user;

import com.turtlebot.operation.dao.UserDAO;
import com.turtlebot.operation.dataobject.User;
import com.turtlebot.operation.dataobject.Users;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述:
 *
 * @outhor didonglin
 * @create 2018-03-17 22:12
 */

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserDAO userDAO;

    public boolean addUser(Integer id) {
        userDAO.addUser(id);
        return false;
    }
    public User getUser(Integer id) {

        User user= userDAO.getUser(id);
        return user;
    }
    public boolean deleteUser(Integer id) {
        userDAO.deleteUser(id);
        return false;
    }
    public List<User> getUserList() {
        List<User> users=userDAO.getUserList();
        return users;
    }

    @Override
    public boolean registUser(Users users) {
        return userDAO.registUser(users);
    }

}