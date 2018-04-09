package com.turtlebot.operation.service.redis;

import com.turtlebot.operation.dataobject.Redis;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

@Service
public class RedisServiceImpl implements RedisService {

    private Redis redis;
    Jedis jedis = new Jedis(redis.getRedisIP());

    @Override
    public boolean insertKV(String key, String value) {
        jedis.set(key,value);
        return false;
    }

    @Override
    public boolean[] insertKVs(HashMap<String, String> hashMap) {
        return new boolean[0];
    }

    @Override
    public String getValue(String key) {
        return null;
    }

    @Override
    public String[] getValues(String[] keys) {
        return new String[0];
    }
}
