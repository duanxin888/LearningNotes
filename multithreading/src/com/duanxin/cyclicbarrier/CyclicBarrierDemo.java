package com.duanxin.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier：
 *
 * @author duanxin
 * @version 1.0
 * @className CyclicBarrierDemo
 * @date 2020/06/20 21:19
 */
public class CyclicBarrierDemo {

    static class TaskThread extends Thread {

        CyclicBarrier cyclicBarrier;

        TaskThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(getName() + " 到达栅栏A");
                cyclicBarrier.await();
                System.out.println(getName() + " 冲破栅栏A");

                Thread.sleep(1000);
                System.out.println(getName() + " 到达栅栏B");
                cyclicBarrier.await();
                System.out.println(getName() + " 冲破栅栏B");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int threadCount = 5;
        CyclicBarrier cyclic = new CyclicBarrier(threadCount, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 完成最后任务");
            }
        });

        for (int i = 0; i < threadCount; ++i) {
            new TaskThread(cyclic).start();
        }
    }
}
