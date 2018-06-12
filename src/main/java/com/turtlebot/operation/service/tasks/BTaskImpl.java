package com.turtlebot.operation.service.tasks;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 11:32
 * @Description:
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BTaskImpl implements IBTask{
    @Scheduled(cron="0/5 * * * * ? ")   //每5秒执行一次
    @Override
    public void bTask(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date())+"*********B任务每5秒执行一次进入测试");
    }
}