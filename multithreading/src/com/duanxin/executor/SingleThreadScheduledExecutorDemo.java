package com.duanxin.executor;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * SingleThreadScheduledExecutor：单一任务线程池，核心线程数为 1
 *
 * @author duanxin
 * @version 1.0
 * @className SingleThreadScheduledExecutorDemo
 * @date 2020/07/04 10:34
 */
public class SingleThreadScheduledExecutorDemo {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        // 简单任务，只执行一次
        service.schedule(() -> {
            System.out.println("start schedule task, time:" + LocalTime.now() + " " + Thread.currentThread().getName());
        }, 1, TimeUnit.SECONDS);

        // 周期性任务，开始延迟执行，之后周期性进行执行
        service.scheduleAtFixedRate(() -> {
            System.out.println("start scheduleAtFixedRate task, time:" + LocalTime.now() + " " + Thread.currentThread().getName());
        }, 5, 1, TimeUnit.SECONDS);

        // 周期性任务，每个周期执行前都要进行延迟
        service.scheduleWithFixedDelay(() -> {
            System.out.println("start scheduleWithFixedDelay task, time:" + LocalTime.now() + " " + Thread.currentThread().getName());
        }, 5, 1, TimeUnit.SECONDS);
    }

}
