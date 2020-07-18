package com.duanxin.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * CompletionService：如何批量执行异步任务？
 * 需求：利用CompletionService实现Dubbo中的Forking	Cluster
 * Dubbo中有一种叫做Forking的集群模式，这种集群模式下，支持并行地调用多个查询服务，只要有一个成
 * 功返回结果，整个服务就可以返回了。例如你需要提供一个地址转坐标的服务，为了保证该服务的高可用和
 * 性能，你可以并行地调用3个地图服务商的API，然后只要有1个正确返回了结果r，那么地址转坐标这个服务
 * 就可以直接返回r了。这种集群模式可以容忍2个地图服务商服务异常，但缺点是消耗的资源偏多。
 *
 * CompletionService 适用于批量执行异步，且可得到执行完成任务的有序性。
 * @author duanxin
 * @version 1.0
 * @className CompletionServiceDemo
 * @date 2020/07/18 20:44
 */
public class CompletionServiceDemo {

    public static void main(String[] args) {
        System.out.println(forkingCluster());
    }

    private static Integer forkingCluster() {
        // create Thread pool
        ExecutorService service = Executors.newFixedThreadPool(3);
        // create CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(service);
        // save Future Object
        List<Future<Integer>> futures = new ArrayList<>();
        // submit task and save future to futures
        cs.submit(CompletionServiceDemo::geocoderByS1);
        cs.submit(CompletionServiceDemo::geocoderByS2);
        cs.submit(CompletionServiceDemo::geocoderByS3);
        // get first result
        Integer r = 0;
        try {
            // only get first result, then break
            for (int i = 0; i < 3; ++i) {
                r = cs.take().get();
                if (r != null) {
                    break;
                }
            }
        } catch (Exception e) {

        } finally {
            // cancel all task
            for (Future<Integer> future : futures) {
                future.cancel(true);
            }
        }
        return r;
    }

    private static Integer geocoderByS1() {
        System.out.println("get first task result");
        return 1;
    }

    private static Integer geocoderByS2() {
        System.out.println("get second task result");
        return 2;
    }

    private static Integer geocoderByS3() {
        System.out.println("get third task result");
        return 3;
    }
}
