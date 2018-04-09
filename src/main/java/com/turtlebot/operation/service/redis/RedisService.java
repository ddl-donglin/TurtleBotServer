package com.turtlebot.operation.service.redis;

import java.util.HashMap;

public interface RedisService {
    boolean insertKV(String key, String value);
    boolean[] insertKVs(HashMap<String,String> hashMap);
    String getValue(String key);
    String[] getValues(String[] keys);

}
