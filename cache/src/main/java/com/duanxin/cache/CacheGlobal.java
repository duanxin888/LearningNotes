package com.duanxin.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * cache global class
 *
 * @author duanxin
 * @version 1.0
 * @className CacheGlobal
 * @date 2020/08/29 10:25
 */
public class CacheGlobal {

    public static ConcurrentHashMap<String, MyCache> cacheMap =
            new ConcurrentHashMap<>();
}
