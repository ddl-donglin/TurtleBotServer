package com.turtlebot.operation.dao;

import com.turtlebot.operation.dataobject.Server;

import java.util.List;


public interface ServerDAO {
    public boolean addServer(String ip,String userName,String password);
    public Server getServer(String ip);
    public boolean delete(String ip);
    public List<Server> getServerList();
}
