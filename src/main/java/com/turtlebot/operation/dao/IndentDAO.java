package com.turtlebot.operation.dao;

import com.turtlebot.operation.dataobject.Indent;

import java.util.List;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 14:48
 * @Description:
 */
public interface IndentDAO {

    List<Indent> getNeedRecvIndents();
    List<Indent> getIndentList();
    boolean sendIndent(Integer orderId);

}
