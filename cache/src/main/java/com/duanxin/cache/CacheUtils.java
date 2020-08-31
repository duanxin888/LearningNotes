package com.duanxin.cache;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * operate cache util
 *
 * @author duanxin
 * @version 1.0
 * @className CacheUtils
 * @date 2020/08/29 10:53
 */
public class CacheUtils {
    
    private CacheUtils() {
        
    }

    /**
     * add cache
     * @param key         key
     * @param value       value
     * @param expiredTime expired time
     * @date 2020/8/29 10:54
     * @return void
     */
    public static void put(String key, Object value, long expiredTime) {
        // check
        if (StringUtils.isBlank(key)) {
            return ;
        }

        ConcurrentHashMap<String, MyCache> map = CacheGlobal.cacheMap;
        // lock
        if (map.containsKey(key)) {
            Lock lock = LockMapUtils.get(key);
            try {
                lock.lock();
                // cache exist then update
                MyCache myCache = map.get(key);
                myCache.setExpiredTime(expiredTime);
                myCache.setHitCount(myCache.getHitCount() + 1);
                myCache.setValue(value);
                myCache.setLastTime(System.currentTimeMillis());
                myCache.setWriteTime(System.currentTimeMillis());
                return;
            } finally {
                lock.unlock();
            }
        }

        // cache not exist then create
        MyCache cache = new MyCache();
        cache.setKey(key);
        cache.setValue(value);
        cache.setWriteTime(System.currentTimeMillis());
        cache.setExpiredTime(expiredTime);
        cache.setLastTime(System.currentTimeMillis());
        cache.setHitCount(1);
        map.put(key, cache);
    }

    /**
     * get value
     * @param key key
     * @date 2020/8/29 11:14
     * @return java.lang.Object
     */
    public static Object get(String key) {
        ConcurrentHashMap<String, MyCache> map = CacheGlobal.cacheMap;
        // check
        if (StringUtils.isBlank(key) || map.isEmpty() || !map.containsKey(key)) {
            return null;
        }
        MyCache cache = map.get(key);
        if (Objects.isNull(cache)) {
            return null;
        }

        // get lock
        Lock lock = LockMapUtils.get(key);
        try {
            // check cache is expired?
            lock.lock();
            long timeout = System.currentTimeMillis() - cache.getWriteTime();
            if (timeout >= cache.getExpiredTime()) {
                // remove cache
                map.remove(key);
                LockMapUtils.remove(key);
                return null;
            }

            // return value
            cache.setHitCount(cache.getHitCount() + 1);
            cache.setLastTime(System.currentTimeMillis());
            return cache.getValue();
        } finally {
            lock.unlock();
        }
    }
}
