package com.turtlebot.operation.service.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface RedisService {
    boolean insertKV(String key, String value);
    ArrayList<Boolean> insertKVs(HashMap<String, String> hashMap);
    String getValue(String key);
    HashMap<String, String> getValues(String[] keys);

}
