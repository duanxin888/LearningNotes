package com.duanxin.thread;

/**
 * 多线程学习的开始：
 * 多线程程序的bug出现原因无非就是 可见性、原子性、有序性带来的问题
 * 比如说：cpu缓存导致的可见性问题，线程切换导致的原子性问题，
 *       编译优化带来的有序性问题（比如单例模式下的双重检测加锁方式存在线程不安全，
 *       但是可以通过加 volatile 来解决）
 *
 * @author duanxin
 * @version 1.0
 * @className SyncDemo
 * @date 2020/06/06 16:39
 */
public class SyncDemo {

    public synchronized void demo1() { // 对象锁
        System.out.println(">>>>>> 以{this}为锁 <<<<<");
    }

    public void demo2() { // 对象锁
        synchronized(this) {
            System.out.println(">>>>>> 以{this}为锁 <<<<<");
        }
    }

    public synchronized static void demo3() { // 全局锁（类锁）
        System.out.println(">>>>>> 以{Sync.class}为锁 <<<<<");
    }

    public void demo4() {
        synchronized(SyncDemo.class) { // 全局锁（类锁）
            System.out.println(">>>>>> 以{Sync.class}为锁 <<<<<");
        }
    }

    public static void main(String[] args) {

    }
}
