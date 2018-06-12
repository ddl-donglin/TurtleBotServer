package com.turtlebot.operation.service;

import com.turtlebot.operation.dao.ServerDAO;
import com.turtlebot.operation.service.tasks.IATask;
import com.turtlebot.operation.service.tasks.IBTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 11:37
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-*.xml"})
public class TaskTest {

    @Autowired
    IATask iaTask;

    @Autowired
    IBTask ibTask;

    @Test
    public void bTaskTest(){
        ibTask.bTask();
    }

    @Test
    public void aTaskTest(){
        iaTask.aTask();
    }

}