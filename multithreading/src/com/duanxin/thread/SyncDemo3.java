package com.duanxin.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 在SyncDemo2例子中，如果账户量小，转账次数少，这种方案还行；
 * 但是当访问并发量增加，每个请求资源线程都不停的循环请求，就大大增加了cpu资源的消耗量，降低程序的性能
 *
 * 因此在本例中，推出 等待-唤醒 机制（wait-notify/notifyAll）
 * @author duanxin
 * @version 1.0
 * @className SyncDemo2
 * @date 2020/06/08 20:05
 */
public class SyncDemo3 {

    public static void main(String[] args) throws InterruptedException {
        Account src = new Account(10000);
        Account target = new Account(10000);
        CountDownLatch countDownLatch = new CountDownLatch(9999);
        for (int i = 0; i < 9999; i++) {
            new Thread(()->{
                src.transfer(target, 1);
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("src=" + src.getBalance());
        System.out.println("target=" + target.getBalance());
    }

    // 破环2示例
    // 场景：对于账户 A 与账户 B 进行转账操作
    static class Allocator {
        private List<Object> als = new ArrayList<>();

        private Allocator(){}

        // 一次性申请所有的资源
        synchronized void apply(Object from, Object to) {
            while (als.contains(from) || als.contains(to)) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 抢占到所有资源
            als.add(from);
            als.add(to);
        }

        // 释放所有资源
        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
            // 唤醒其他等待线程
            notifyAll();
        }

        public static Allocator getInstance() {
            return AllocatorSingle.instance;
        }

        static class AllocatorSingle {
            public static Allocator instance = new Allocator();
        }
    }

    static class Account {
        private int balance;

        Account(int balance) {
            this.balance = balance;
        }

        void transfer(Account target, int amt) {
            Allocator.getInstance().apply(this, target);
            this.balance -= amt;
            target.balance += amt;
            Allocator.getInstance().free(this, target);
        }

        int getBalance() {
            return this.balance;
        }
    }
}
