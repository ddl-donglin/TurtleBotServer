package com.turtlebot.operation.service.tasks;

import com.turtlebot.operation.dataobject.Indent;

import java.util.List;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 14:41
 * @Description:
 */
public interface OrderTask {

    /**
     * 每隔5秒查看一下订单是否有更新
     */
    List<Indent> getNeedRecvIndents();

    List<Indent> getIndentList();

    void sendIndent();

}
