package com.duanxin.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SingleThreadExecutor：单一线程池，核心线程数和最大线程数都为 1，
 * 任务队列为 LinkedBlockingQueue
 *
 * @author duanxin
 * @version 1.0
 * @className SingleThreadExecutorDemo
 * @date 2020/07/04 10:04
 */
public class SingleThreadExecutorDemo {

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 100; ++i) {
            service.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
    }
}
