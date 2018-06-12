package com.turtlebot.operation.web;


import com.turtlebot.operation.dataobject.User;
import com.turtlebot.operation.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
    public String getUserList(ModelMap modelMap){
        List<User> userList=userService.getUserList();
        modelMap.put("UserList",userList);
        return "UserList";
    }

    @RequestMapping(value = "/registUser",method = RequestMethod.GET)
    public String registUser(ModelMap modelMap){
        return "index";
    }

}
