package com.turtlebot.operation.dao;

import com.turtlebot.operation.dataobject.Dispatch;

/**
 * Created by kewenkang on 2018/6/26/0026.
 */
public interface DispatchDAO {
    boolean addDispatch(Dispatch dispatch);
    boolean updateDispatch(Dispatch dispatch);
}
