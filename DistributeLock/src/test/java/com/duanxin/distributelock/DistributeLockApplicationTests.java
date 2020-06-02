package com.duanxin.distributelock;

import com.duanxin.distributelock.utils.ZkLock;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DistributeLockApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributeLockApplicationTests.class);

    @Test
    void contextLoads() {
    }

    @Test
    public void testCurator() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.100.160:2181", retryPolicy);
        client.start();
        InterProcessMutex lock = new InterProcessMutex(client, "/order");
        try {
            if (lock.acquire(30, TimeUnit.SECONDS)) {
                try {
                    LOGGER.info("获得了锁");
                } finally {
                    lock.release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZkLock() {
        try (ZkLock zkLock = new ZkLock()) {
            boolean lock = zkLock.lock("order");
            if (lock) {
                System.out.println("获取锁成功！！");
            }
        } catch (Exception e) {
            System.out.println("操作失败，原因:" + e);
        }
    }
}
