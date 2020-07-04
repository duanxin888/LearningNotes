package com.duanxin.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 需求：在“烧水泡茶”中，要进行完洗水壶、烧开水、洗茶壶、洗茶杯、拿茶叶5个步骤才能进行泡茶阶段，
 * 而在分析中，可以将洗茶壶、烧开水和洗茶壶、洗茶杯、拿茶叶分成两个大的步骤，分别让两个线程去完成，
 * 设 ft1 完成洗茶壶、烧开水，而 ft2 完成洗茶壶、洗茶杯、拿茶叶，然而 ft1 需要的时间更长，
 * 因此 ft2 需要进行等待。
 *
 *
 * @author duanxin
 * @version 1.0
 * @className FutureDemo
 * @date 2020/07/03 22:11
 */
public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建 T1 的 FutureTask
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
        // 创建 T2 的 FutureTask
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));

        // 启动T1 T2
        new Thread(ft1).start();
        new Thread(ft2).start();

        System.out.println(ft1.get());
    }

    /**
     * T1Task 需要执行的任务：洗水壶、烧开水、泡茶
     * */
    static class T1Task implements Callable<String> {

        FutureTask<String> ft2;

        T1Task(FutureTask<String> ft2) {
            this.ft2 = ft2;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1：洗茶水");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T1：烧开水");
            TimeUnit.SECONDS.sleep(15);

            // 获取T2线程的茶水
            String tf = ft2.get();
            System.out.println("T1：拿到茶叶：" + tf);

            System.out.println("T1：泡茶");

            return "上茶：" + tf;
        }
    }

    /**
     * T2Task: 执行任务 洗茶壶、洗茶杯、拿茶叶
     * */
    static class T2Task implements Callable<String> {

        @Override
        public String call() throws Exception {

            System.out.println("T2：洗茶壶。。。");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T2：洗茶杯。。。");
            TimeUnit.SECONDS.sleep(2);

            System.out.println("T2：拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            return "大红袍";
        }
    }


}
