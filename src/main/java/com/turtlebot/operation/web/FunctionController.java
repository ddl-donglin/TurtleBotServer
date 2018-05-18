package com.turtlebot.operation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/function")
public class FunctionController {



    @RequestMapping(value = "/repair",method = RequestMethod.GET)
    public void repair(String charactor, HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");
//        System.out.println(new String(charactor.getBytes("ISO8859-1"), "UTF-8"));
        System.out.println(charactor);
        response.getWriter().print(charactor);
    }
}
