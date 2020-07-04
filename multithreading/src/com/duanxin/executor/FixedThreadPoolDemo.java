package com.duanxin.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FixedThreadPool：固定线程池，核心线程数和最大线程数一致，
 * 采用的任务队列为 LinkedBlockingQueue，采用默认的线程工厂和拒绝策略
 *
 * @author duanxin
 * @version 1.0
 * @className FixedThreadPoolDemo
 * @date 2020/07/04 10:01
 */
public class FixedThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; ++i) {
            service.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
    }

}
