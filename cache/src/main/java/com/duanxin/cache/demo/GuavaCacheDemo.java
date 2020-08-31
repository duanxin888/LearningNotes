package com.duanxin.cache.demo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author duanxin
 * @version 1.0
 * @className GuavaCacheDemo
 * @date 2020/08/29 09:50
 */
public class GuavaCacheDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建方式一：LoadingCache
        LoadingCache<String, String> cacheLoader = CacheBuilder.newBuilder().
                concurrencyLevel(10). // 同时写缓存的线程并发数
                expireAfterWrite(Duration.ofSeconds(5)). // 设置缓存过期时间
                initialCapacity(5). // 设置缓存初始容量
                maximumSize(100). // 设置缓存最大容量，超过之后以LRU策略进行淘汰数据
                recordStats(). // 设置要统计缓存命中率
                removalListener((notification) -> // 设置移除缓存通知
                        System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause())).
                build(new CacheLoader<String, String>() { // 指定 CacheLoader，缓存不存在时，可自动加载缓存
                    @Override
                    public String load(String key) throws Exception {
                        return "cache-value:" + key;
                    }
                });
        // 设置缓存， k-v 存储
        cacheLoader.put("name", "Chris");
        cacheLoader.put("age", "21");
        System.out.println(cacheLoader.get("name"));
        TimeUnit.SECONDS.sleep(5L);
        // 测试缓存过期时间
        System.out.println(cacheLoader.get("age"));


        System.out.println("--------------------------------------------");

        // 创建方式二：Callable
        Cache<String, String> cache = CacheBuilder.newBuilder().
                maximumSize(2). // 设置最大缓存长度
                build();
        cache.put("name", "Chris");
        cache.put("age", "21");
        // 查询存在的值
        System.out.println(cache.get("name", () -> "nil"));
        // 查询不存在的值
        System.out.println(cache.get("sex", () -> "nil"));
    }

}
