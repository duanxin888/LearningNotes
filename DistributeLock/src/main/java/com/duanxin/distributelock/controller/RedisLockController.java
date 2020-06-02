package com.duanxin.distributelock.controller;

import com.duanxin.distributelock.utils.RedisLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author duanxin
 * @version 1.0
 * @className RedisLockController
 * @date 2020/05/31 14:41
 */
@RestController
public class RedisLockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockController.class);

    @Resource
    private RedisLock redisLock;

    @Resource
    private RedissonClient redissonClient;


    /**
     * 使用 redisson 实现分布式锁
     *
     * @return java.lang.String
     * @date 2020/6/2 15:29
     */
    @GetMapping("/redissonLock")
    public String redissonLock() {
        LOGGER.info("进入方法");
        RLock lock = redissonClient.getLock("order");
        try {
            LOGGER.info("获取锁");
            lock.lock(30, TimeUnit.SECONDS);
            Thread.sleep(15000);
        } catch (Exception e) {
            LOGGER.error("出现异常,{}", e);
        } finally {
            LOGGER.info("释放锁");
            lock.unlock();
        }
        return "操作完成";
    }

    /**
     * 分布式锁实现（基于redis nx机制）
     * 原理：利用 NX 的原子性，多个线程并发时，只有一个线程可以设置成功，
     * 设置成功即获取锁，可以进行后续的业务操作，
     * 如果出现异常，过了锁的有效期，锁自动释放；
     * 释放锁采用 Redis 的 delete命令，
     * 释放锁时校验之前设置的随机数，相同才释放，
     * 释放锁使用 LUA 脚本
     */
    @GetMapping("redisLock")
    public String redisLock() {
        LOGGER.info("我进入方法了");

        String key = "redisKey";
        String value = UUID.randomUUID().toString().trim();
        long expireTime = 30L;
        boolean lock = redisLock.lock(key, value, expireTime);
        if (lock) {
            LOGGER.info("我获取到锁了");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                boolean r = redisLock.unlock(key, value);
                LOGGER.info("释放锁的结果:{}", r);
            }
        }
        LOGGER.info("方法执行完成");
        return "我已经完成了";
    }
}
