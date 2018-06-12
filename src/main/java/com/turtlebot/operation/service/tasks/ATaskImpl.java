package com.turtlebot.operation.service.tasks;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 11:21
 * @Description:
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
@Component
public class ATaskImpl implements IATask {
    @Scheduled(cron="0/10 * * * * ? ") //每隔10秒
    @Override
    public void aTask() {
try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + "*********A任务每10秒执行一次进入测试");
    }
}
*/
