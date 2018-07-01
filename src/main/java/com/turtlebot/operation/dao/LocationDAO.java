package com.turtlebot.operation.dao;

import com.turtlebot.operation.dataobject.Location;

/**
 * Created by kewenkang on 2018/6/26/0026.
 */
public interface LocationDAO {
    Location getLocationByGoods(Integer goodsId);

}
