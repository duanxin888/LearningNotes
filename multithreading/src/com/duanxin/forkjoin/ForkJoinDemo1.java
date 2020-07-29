package com.duanxin.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 对于简单的并行任务，可以通过“线程池 + Future”的方式来解决；如果任务之间存在聚合，
 * 无论是AND聚合还是OR聚合，都可以通过 CompletableFuture 来解决；而批量的并行任务，
 * 有 CompletionService 可以解决。
 *
 * 在更加复杂的分治场景中，还有 Fork/Join 可以解决。Fork是对分治任务模型里面的任务分解，
 * Join对应的是结果的合并。
 *
 * 在其他的线程池内部只有一个任务队列，而在 ForkJoinPool 中存在多个任务队列（双端队列），
 * 并且还存在一个“任务窃取”机制，当其中某个工作线程空闲了，就会“窃取”其他工作任务队列中的任务。
 *
 * 需求：使用 Fork/Join 解决斐波那契数列问题
 *
 * @author duanxin
 * @version 1.0
 * @className ForkJoinDemo1
 * @date 2020/07/29 21:51
 */
public class ForkJoinDemo1 {

    public static void main(String[] args) {
        // 创建分治任务线程池
        ForkJoinPool fjp = new ForkJoinPool(4);
        // 创建分治任务
        Fibonacci fibonacci = new Fibonacci(15);
        // 启动分治任务
        Integer result = fjp.invoke(fibonacci);
        System.out.println(result);
    }

    static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            // 创建子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            // 等待子任务完成，并合并结果
            return f2.compute() + f1.join();
        }
    }

}
