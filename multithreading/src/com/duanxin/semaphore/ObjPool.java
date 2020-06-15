package com.duanxin.semaphore;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 对象池：通过 Semaphore信号量对象进行设计实现
 * Semaphore：一个计数器，一个等待队列，三个方法（init, acquire, release）
 *
 * 在下面对象池的例子中，对象保存在了 Vector 中，Vector 是 Java 提供的线程安全的容器，如
 * 果我们把 Vector 换成 ArrayList，是否可以呢？
 *      不行，对于Semaphore来说，可以多个线程同时进入临界区，这个时候对对象池来说，
 *      就必须进行锁保护使得线程安全。
 *
 * @author duanxin
 * @version 1.0
 * @className ObjPool
 * @date 2020/06/15 14:38
 */
public class ObjPool<T, R> {

    // 对象池
    final List<T> pool;

    // 用信号量实现限流器
    final Semaphore sem;

    // 构造方法
    ObjPool(int size, T t) {
        pool = new Vector<T>(){};
        for (int i = 0; i < size; ++i) {
            pool.add(t);
        }
        sem = new Semaphore(size);
    }

    // 利用对象池的对象，调用exec
    R exec(Function<T, R> func) {
        T t = null;
        try {
            sem.acquire();
            t = pool.remove(0);
            return func.apply(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.add(t);
            sem.release();
        }
        return null;
    }

    // 测试
    public static void main(String[] args) {
        // 创建对象池
        ObjPool<Long, String> pool = new ObjPool<Long, String>(10, 2L);
        // 通过对象池获取 t，之后执行
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }
}
