package com.turtlebot.operation.service;

import com.turtlebot.operation.dao.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述:
 *
 * @outhor didonglin
 * @create 2018-03-17 22:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-*.xml"})
public class UserTest {

    @Autowired
    UserDAO userDAO;

    @Test
    public void UserTest(){
        System.out.println(userDAO.getUserList());
    }

}