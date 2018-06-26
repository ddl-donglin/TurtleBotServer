package com.turtlebot.operation.service.trolleys;

import com.turtlebot.operation.dataobject.Indent;

import java.util.HashMap;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 15:21
 * @Description:
 */
public interface TrolleyService {

    Integer orderDispatch(Indent indent);  // 返回的是派发任务的小车的编号

    HashMap<Float, Float> getTrolleyLocation(Integer trolleyId);  // 根据小车id获取当前位置，结果是（x，y）

    HashMap<Float, Float> getGoodsLocation(Integer goodsId); // 根据商品id获取商品位置，结果为（x，y）

}
