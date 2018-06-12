package com.turtlebot.operation.service.trolleys;

import com.turtlebot.operation.dataobject.Indent;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 15:21
 * @Description:
 */
public interface TrolleyService {

    Integer orderDispatch(Indent indent);  // 返回的是派发任务的小车的编号

}
