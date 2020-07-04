package com.duanxin.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author duanxin
 * @version 1.0
 * @className ThreadPoolExecutorDemo
 * @date 2020/07/04 09:45
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        ExecutorService service =
                new ThreadPoolExecutor(5,  // 核心线程数，即最小线程数
                        10,           // 最大线程数
                        1,               // 当除核心线程外的线程处于空闲状态下的销毁时间
                        TimeUnit.SECONDS,             // 销毁时间单位
                        new ArrayBlockingQueue<Runnable>(10),  // 任务队列
                        (r) -> {                      // 线程工厂，创建新线程的方式、策略
                            Thread t = new Thread(r);
                            t.setName("ThreadPoolExecutorDemo" + r.getClass().getName());
                            return t;
                        },
                        (r, executor) -> {            // 当线程数达到最大值并且任务队列也满，当有新的任务进来时采用的拒绝方式
                            System.out.println("executor is full, not into thread");
                        });
        for (int i = 0; i < 100; ++i) {
            service.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
    }


}
