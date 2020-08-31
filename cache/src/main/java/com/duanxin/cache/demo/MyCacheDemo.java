package com.duanxin.cache.demo;

import com.duanxin.cache.ExpiredThread;

import java.util.concurrent.TimeUnit;

import static com.duanxin.cache.CacheUtils.get;
import static com.duanxin.cache.CacheUtils.put;

/**
 * test MyCache
 *
 * @author duanxin
 * @version 1.0
 * @className MyCacheDemo
 * @date 2020/08/29 11:20
 */
public class MyCacheDemo {

    public static void main(String[] args) throws InterruptedException {
        // start expired task
        new Thread(new ExpiredThread()).start();
        put("name", "Chris", 10000);
        put("age", "21", 20000);
        System.out.println(get("name"));
        System.out.println(get("age"));
        System.out.println("--------------------------------");
        TimeUnit.SECONDS.sleep(5);
        System.out.println(get("name"));
        System.out.println(get("age"));
        System.out.println("------------------------------");
        TimeUnit.SECONDS.sleep(10);
        System.out.println(get("name"));
        System.out.println(get("age"));
        System.out.println("------------------------------");
        TimeUnit.SECONDS.sleep(5);
        System.out.println(get("name"));
        System.out.println(get("age"));

    }
}
