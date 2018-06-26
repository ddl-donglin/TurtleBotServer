package com.turtlebot.operation.dao;

import com.turtlebot.operation.dataobject.Trolley;

import java.util.List;

/**
 * Created by kewenkang on 2018/6/26/0026.
 */
public interface TrolleyDAO {
    List<Trolley> getAllActiveAndRemainTrolleys();
    boolean updateTrolley(Trolley trolley);
    Trolley getTrolleyById(Integer id);
}
