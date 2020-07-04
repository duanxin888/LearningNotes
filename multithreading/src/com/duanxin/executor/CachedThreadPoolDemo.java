package com.duanxin.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CachedThreadPool：缓存线程池，核心线程数为0，最大线程数为 Integer.MAX_VALUE
 * 任务队列 SynchronousQueue（该阻塞队列没有容量，下一个任务必须等待上一个执行完毕并且删除才能进入队列中）
 *
 * @author duanxin
 * @version 1.0
 * @className CachedThreadPoolDemo
 * @date 2020/07/04 10:08
 */
public class CachedThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; ++i) {
            service.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
    }
}
