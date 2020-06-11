package com.duanxin.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 死锁：两个线程互相等待对方的资源释放而进行的阻塞情况叫做死锁。
 * 死锁产生的原因（四个条件缺一不可）：
 *      1、互斥性，共享资源 X 和共享资源 Y 自能被一个线程占有。
 *      2、抢占且等待，线程 T1 在占有 X 资源，在等待共享资源 Y，但是不释放自己占有的资源 X。
 *      3、不可抢夺性，其他线程不能抢占线程 T1 占有的资源。
 *      4、循环等待，线程 T1 等待线程 T2 释放资源，线程 T2 等待线程 T1 释放资源，两个线程互相等待。
 *
 * 只要破环其中一个就可解开线程死锁问题
 *
 * 破环2：对于线程来说要么全部把资源给它，要么全不给
 * 破环3：使用 JUC 中的Lock
 * 破环4：对锁资源进行优先级排序
 *
 * @author duanxin
 * @version 1.0
 * @className SyncDemo2
 * @date 2020/06/08 20:05
 */
public class SyncDemo2 {

    public static void main(String[] args) {
        Allocator allocator = new Allocator();
        Account a = new Account(allocator, 1000);
        Account b = new Account(allocator, 800);

        new Thread(() -> {
            System.out.println(">>>>>>>账户A转账 100 给账户B<<<<<<<<<" + Thread.currentThread().getName());
            a.transfer(b, 100);
            System.out.println("转账结束，账户A：" + a.getBalance() + "::账户B：" + b.getBalance());
        }).start();

        new Thread(() -> {
            System.out.println(">>>>>>>账户B转账 200 给账户A<<<<<<<<<" + Thread.currentThread().getName());
            b.transfer(a, 200);
            System.out.println("转账结束，账户A：" + a.getBalance() + "::账户B：" + b.getBalance());
        }).start();
    }

    // 破环2示例
    // 场景：对于账户 A 与账户 B 进行转账操作
    static class Allocator {
        private List<Object> als = new ArrayList<>();

        // 一次性申请所有的资源
        synchronized boolean apply(Object from, Object to) {
            if (als.contains(from) || als.contains(to)) {
                return false;
            } else {
                als.add(from);
                als.add(to);
            }
            return true;
        }

        // 释放所有资源
        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
        }
    }

    static class Account {
        private Allocator allocator;
        private int balance;

        Account(Allocator allocator, int balance) {
            this.allocator = allocator;
            this.balance = balance;
        }

        void transfer(Account target, int amt) {
            while (!allocator.apply(this, target)); // 尝试获取所有资源
            try {
                synchronized (this) { // 锁定转出账户
                    synchronized(target) { // 锁定转入账户
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            } finally { // 释放所有资源
                allocator.free(this, target);
            }
        }

        int getBalance() {
            return this.balance;
        }
    }
}
