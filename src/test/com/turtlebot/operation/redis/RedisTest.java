package com.turtlebot.operation.redis;

import com.turtlebot.operation.service.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-*.xml"})
public class RedisTest {

    @Autowired
    RedisService redisService;

    @Test
    public void redisTest(){
        //System.out.println(redisService.insertKV("redisJunittest01", "RedisJunitTestvalue01"));
        //System.out.println(redisService.getValue("redisJunittest01"));
        /*HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key00001", "value00001");
        hashMap.put("key00002", "value00002");
        hashMap.put("key00003", "value00003");

        System.out.println(redisService.insertKVs(hashMap));*/

        System.out.println(redisService.getValues(new String[]{"key00001","key00003"}));
    }

}

