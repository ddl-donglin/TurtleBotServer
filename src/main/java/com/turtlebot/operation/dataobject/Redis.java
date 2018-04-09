package com.turtlebot.operation.dataobject;

import com.turtlebot.operation.service.redis.RedisServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Redis {

    private static String redisIP;

    static {
        InputStream inputStream = RedisServer.class.getResourceAsStream("/redis.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            redisIP = properties.getProperty("redis.ip");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRedisIP() {
        return redisIP;
    }

    public void setRedisIP(String redisIP) {
        this.redisIP = redisIP;
    }

}
