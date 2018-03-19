package com.turtlebot.operation.service;

import com.turtlebot.operation.dao.ServerDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述:
 *
 * @outhor didonglin
 * @create 2018-03-17 21:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-*.xml"})
public class ServerTest {

    @Autowired
    ServerDAO serverDAO;

    @Test
    public void ServerTest(){
        System.out.println(serverDAO.getServerList());
    }
}