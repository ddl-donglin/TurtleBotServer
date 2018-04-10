package com.turtlebot.operation.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * 描述:
 *
 * @outhor didonglin
 * @create 2018-03-19 16:59
 */

public class RedisString {

    @Autowired
    RedisService redisService;

    public void setStringKV(String host, String key, String value){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis(host);
        System.out.println("连接成功");
        //设置 redis 字符串数据
        System.out.println(jedis.set(key, value));
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get(key));
    }

    public static void main(String[] args) {
        //new RedisString().setStringKV("192.168.223.10","testkeyfromjava002", "testvaluefromjava002");
    }
}