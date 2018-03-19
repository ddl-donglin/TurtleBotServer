package com.turtlebot.operation.service.server;

import com.turtlebot.operation.dao.ServerDAO;
import com.turtlebot.operation.dataobject.Server;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService{

    @Resource
    private ServerDAO serverDAO;

    public boolean addServer(String ip, String userName, String password) {
        serverDAO.addServer(ip,userName,password);
        return false;
    }
    public Server getServer(String ip) {
        Server server= serverDAO.getServer(ip);
        return server;
    }
    public boolean delete(String ip) {
        serverDAO.delete(ip);
        return false;
    }
    public List<Server> getServerList() {
        List<Server> servers=serverDAO.getServerList();
        return servers;
    }
}
