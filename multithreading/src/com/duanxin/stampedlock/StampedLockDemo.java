package com.duanxin.stampedlock;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock：比读写锁 ReaDWriteLock 更快的锁
 * ReadWriteLock 支持两种模式：一种是读锁，一种是写锁。而 StampedLock 支持三种模式，分别
 * 是：写锁、悲观读锁和乐观读。其中，写锁、悲观读锁的语义和 ReadWriteLock 的写锁、读锁的
 * 语义非常类似，允许多个线程同时获取悲观读锁，但是只允许一个线程获取写锁，写锁和悲观读
 * 锁是互斥的。
 * 乐观读这个操作是无锁的，所以相比较 ReadWriteLock 的读锁，乐观读的性能更好一些.
 *
 * 注意事项：
 *      1、StampedLock 只是 ReadWriteLock 的子集，在读多写少、简单场景适合 StampedLock。
 *      2、StampedLock 不支持重入。
 *      3、StampedLock 一定不要调用中断操作，如果需要支持中断功能，一定使用可中断的
 *          悲观读锁 readLockInterruptibly() 和写锁 writeLockInterruptibly()。
 * @author duanxin
 * @version 1.0
 * @className StampedLockDemo
 * @date 2020/06/20 15:54
 */
public class StampedLockDemo {

    static class Point {
        private double x;
        private double y;

        private final StampedLock stampedLock = new StampedLock();

        /**
         * 移动
         * */
        void move(double deltaX, double deltaY) {
            // 获取写锁
            long stamp = stampedLock.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        }

        /**
         * 距离原地长度
         * */
        double distanceFromOrigin() {
            // 获取乐观读锁
            long stamp = stampedLock.tryOptimisticRead();
            double currentX = x;
            double currentY = y;
            // 判断是否有其他线程在进行写操作
            if (!stampedLock.validate(stamp)) { // 有写操作线程
                // 获取悲观读锁
                stamp = stampedLock.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            // 返回结果
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        /**
         * 从原点移动位置
         * */
        void moveIfOrigin(double newX, double newY) {
            // 获取悲观读锁
            long stamp = stampedLock.readLock();
            try {
                while (x == 0.0 && y == 0.0) { // 坐标在原点
                    // 尝试升级为写锁
                    long ws = stampedLock.tryConvertToWriteLock(stamp);
                    if (ws != 0L) { // 升级成功
                        stamp = ws;
                        x = newX;
                        y = newY;
                        break;
                    } else { // 升级失败，手动释放读锁，获取写锁
                        stampedLock.unlockRead(stamp);
                        stamp = stampedLock.writeLock();
                    }
                }
            } finally {
                // 释放锁资源
                stampedLock.unlock(stamp);
            }
        }
    }
}
