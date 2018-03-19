package com.turtlebot.operation.web;

import com.turtlebot.operation.dataobject.Server;
import com.turtlebot.operation.service.server.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/server")
public class ServerController {

    @Resource
    private ServerService serverService;

    @RequestMapping(value = "/getServerList",method = RequestMethod.GET)
    public String getServerList(ModelMap modelMap){
        List<Server> serverList=serverService.getServerList();
        modelMap.put("serverList",serverList);
        return "serverList";
    }
}
