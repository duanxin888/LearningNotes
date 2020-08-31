package com.duanxin.cache.demo;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * @author duanxin
 * @version 1.0
 * @className EhcacheDemo
 * @date 2020/08/29 09:44
 */
public class EhcacheDemo {

    public static void main(String[] args) {
        // 创建缓存管理器
        CacheManager cacheManager =
                CacheManagerBuilder.newCacheManagerBuilder().build();
        // 初始化
        cacheManager.init();

        // 创建缓存（存储器）
        Cache<String, String> cache = cacheManager.createCache("MyCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class, String.class,
                        ResourcePoolsBuilder.heap(10))); // 创建缓存最大容量
        // 设置缓存，使用hash存储方式存储多个缓存
        cache.put("name", "Chris");
        cache.put("age", "21");
        // 读取输出缓存
        System.out.println(cache.get("name"));
        // 关闭缓存管理器
        cacheManager.close();
    }

}
