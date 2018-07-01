package com.turtlebot.operation.DAO;

import com.turtlebot.operation.dao.LocationDAO;
import com.turtlebot.operation.dao.TrolleyDAO;
import com.turtlebot.operation.dataobject.Location;
import com.turtlebot.operation.dataobject.OrderItem;
import com.turtlebot.operation.dataobject.Trolley;
import com.turtlebot.operation.service.trolleys.TrolleyService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by kewenkang on 2018/6/26/0026.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-*.xml"})
public class Test {
    @Autowired
    TrolleyDAO trolleyDAO;
    @Autowired
    LocationDAO locationDAO;
    @Autowired
    TrolleyService trolleyService;

    @org.junit.Test
    public void trolleyTest(){
        List<Trolley> trolleys = trolleyDAO.getAllActiveAndRemainTrolleys();
        System.out.println(trolleys);
    }

    @org.junit.Test
    public void updateTrolleyTest(){
        Trolley trolley = trolleyDAO.getTrolleyById(1);
        trolley.setRemainCapacity(trolley.getRemainCapacity()+1);
        trolleyDAO.updateTrolley(trolley);
        System.out.println(trolley);
    }

    @org.junit.Test
    public void locationTest(){
        Location location = locationDAO.getLocationByGoods(73);
        System.out.println(location);
    }
    @org.junit.Test
    public void leftOrderTest(){
        List<OrderItem> orderItems = trolleyService.getUnsentOrderItem();
        System.out.println(orderItems);
    }
    @org.junit.Test
    public void bestDispatchTest(){
        trolleyService.bestDispatch();
    }


}
