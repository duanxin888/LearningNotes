package com.duanxin.future;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 需求：如果想要搭建一个旅游平台，经常会有这样的需求，
 * 那就是用户想同时获取多家航空公司的航班信息。
 * 比如，从北京到上海的机票钱是多少？有很多家航空公司都有这样的航班信息，
 * 所以应该把所有航空公司的航班、票价等信息都获取到，然后再聚合。
 * 由于每个航空公司都有自己的服务器，所以分别去请求它们的服务器就可以了，
 * 比如请求国航、海航、东航等。
 *
 * 这是个并行异步请求，可以使用 线程池、CountDownLatch、CompletableFuture实现。
 *
 * @author duanxin
 * @version 1.0
 * @className CompletableFutureDemo
 * @date 2020/07/18 15:40
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        System.out.println(new CompletableFutureDemo().getPrices());
    }

    private Set<Integer> getPrices() {
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(new Task(123, prices));
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(new Task(456, prices));
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(new Task(789, prices));
        CompletableFuture<Void> allTask = CompletableFuture.allOf(task1, task2, task3);
        try {
            // wait 3s, if all task completed, so don`t wait
            allTask.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return prices;
    }

    private class Task implements Runnable {

        Integer productId;
        Set<Integer> prices;

        public Task(Integer productId, Set<Integer> prices) {
            this.prices = prices;
            this.productId = productId;
        }

        @Override
        public void run() {
            int price = 0;
            try {
                Thread.sleep((long) (Math.random() * 4000));
                price = (int) (Math.random() * 4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prices.add(price);
        }
    }

}
