package com.duanxin.distributelock.utils;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * redis 分布式锁工具类
 * @author duanxin
 * @version 1.0
 * @className RedisLock
 * @date 2020/06/01 15:09
 */
@Component
public class RedisLock {

    /** 执行redis命令 */
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取分布式锁
     * @param key 键
     * @param value 值
     * @param expireTime 超时时间，单位秒
     * @date 2020/6/1 15:18
     * @return boolean
     */
    public boolean lock(String key, String value, long expireTime) {
        RedisCallback<Boolean> redisCallback = connection -> {
            // 设置过期时间
            Expiration expiration = Expiration.seconds(expireTime);
            // 设置 NX
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            // key value
            byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);
            byte[] redisValue = redisTemplate.getValueSerializer().serialize(value);
            return connection.set(redisKey, redisValue, expiration, setOption);
        };

        // 获取分布式锁
        Boolean lock = (Boolean) redisTemplate.execute(redisCallback);
        return lock;
    }

    /**
     * 释放分布式锁
     * @param key 键
     * @param value 值
     * @date 2020/6/1 15:18
     * @return boolean
     */
    public boolean unlock(String key, String value) {
        String script = "if redis.call(\"get\", KEYS[1]) == ARGV[1] then\n" +
                " return redis.call(\"del\", KEYS[1])\n" +
                "else\n" +
                " return 0\n" +
                "end";
        RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
        List<String> keys = Collections.singletonList(key);
        Boolean r = (Boolean) redisTemplate.execute(redisScript, keys, value);
        return r;
    }
}
