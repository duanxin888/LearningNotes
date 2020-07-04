package com.duanxin.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch（共享锁）：
 *      应用：计数器
 *      底层原理：await 方法阻塞调用线程，调用线程进入AQS同步队列中被挂起；
 *      而 countDown 方法就是使 state 减1，然后检验 state 是否小于 1，如果小于1，
 *      则唤醒所有在同步队列挂起的线程，如果大于1，继续等待下一个 countDown 被调用
 *
 * @author duanxin
 * @version 1.0
 * @className CountDownLatchDemo
 * @date 2020/06/15 20:15
 */
public class CountDownLatchDemo implements Runnable{

    static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread:" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; ++i) {
            new Thread(new CountDownLatchDemo()).start(); // 三个线程都阻塞
        }
        // 计数器减1，查看计数器是否为0进而唤醒所有阻塞线程
        // 并且所有线程抢占锁，CountDownLatch内置的是共享锁
        countDownLatch.countDown();
    }
}
