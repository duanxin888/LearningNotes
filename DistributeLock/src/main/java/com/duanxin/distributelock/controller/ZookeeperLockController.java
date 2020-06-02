package com.duanxin.distributelock.controller;

import com.duanxin.distributelock.utils.ZkLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 使用 Zookeeper 实现分布式锁
 * @author duanxin
 * @version 1.0
 * @className ZookeeperLockController
 * @date 2020/06/01 16:10
 */
@RestController
public class ZookeeperLockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperLockController.class);

    @Resource
    private CuratorFramework client;

    /**
     * 使用 Zookeeper 原生api 实现分布式锁
     * @date 2020/6/2 15:04
     * @return java.lang.String
     */
    @GetMapping("/zkLock")
    public String zkLock() {
        String businessCode = "order";
        try(ZkLock zkLock = new ZkLock()) {
            boolean lock = zkLock.lock(businessCode);
            if (lock) {
                LOGGER.info("获取锁成功！！");
            }
            Thread.sleep(15000);
        } catch (Exception e) {
            LOGGER.error("操作失败，原因:{}", e);
        }
        return "操作完成了";
    }

    /**
     * 使用 curator 客户端实现分布式锁
     * @date 2020/6/2 15:03
     * @return java.lang.String
     */
    @GetMapping("/curatorLock")
    public String curatorLock() {
        LOGGER.info("进入方法");
        String business = "/order";
        InterProcessMutex lock = null;
        try {
            lock = new InterProcessMutex(client, business);
            if (lock.acquire(30, TimeUnit.SECONDS)) {
                LOGGER.info("获取锁成功");
            }
            Thread.sleep(15000);
        } catch (Exception e) {
            LOGGER.error("操作失败，原因:{}", e);
        } finally {
            try {
                Objects.requireNonNull(lock).release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "操作完成了";
    }
}
