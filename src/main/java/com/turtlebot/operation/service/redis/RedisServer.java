package com.turtlebot.operation.service.redis;

import redis.clients.jedis.Jedis;

/**
 * 描述:
 *
 * @outhor didonglin
 * @create 2018-03-19 16:57
 */
public class RedisServer {

    public void redisStartTest(String host) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis(host);
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
    }

    public static void main(String[] args) {
        new RedisServer().redisStartTest("localhost");
    }
}