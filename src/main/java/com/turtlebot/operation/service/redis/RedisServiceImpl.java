package com.turtlebot.operation.service.redis;

import com.turtlebot.operation.dataobject.Redis;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {

    Jedis jedis = new Jedis(Redis.getRedisIP());

    @Override
    public boolean insertKV(String key, String value) {
        return  jedis.set(key,value).equalsIgnoreCase("OK");
    }

    @Override
    public ArrayList<Boolean> insertKVs(HashMap<String, String> hashMap) {
        ArrayList<Boolean> res = new ArrayList<>();
        for(String key : hashMap.keySet())
            res.add(jedis.set(key,hashMap.get(key)).equalsIgnoreCase("OK"));
        return res;
    }

    @Override
    public String getValue(String key) {
        return jedis.get(key);
    }

    @Override
    public HashMap<String, String> getValues(String[] keys) {
        HashMap<String, String> hashMap = new HashMap<>();
        for(String key : keys)
            hashMap.put(key,jedis.get(key));
        return hashMap;
    }


}
