package com.turtlebot.operation.dao;

import com.turtlebot.operation.dataobject.OrderItem;

import java.util.List;

/**
 * Created by kewenkang on 2018/6/26/0026.
 */
public interface OrderItemDAO {
    List<OrderItem> getOrderItemByOrderId(Integer id);

}
