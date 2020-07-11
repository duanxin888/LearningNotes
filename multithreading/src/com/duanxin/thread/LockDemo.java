package com.duanxin.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duanxin
 * @version 1.0
 * @className LockDemo
 * @date 2020/07/05 15:23
 */
public class LockDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.unlock();
        } catch (Exception e) {
            System.out.println("抛出释放锁异常");
            e.printStackTrace();
        }
    }

}
