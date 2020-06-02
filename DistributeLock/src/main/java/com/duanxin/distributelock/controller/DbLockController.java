package com.duanxin.distributelock.controller;

import com.duanxin.distributelock.entity.DistributeLock;
import com.duanxin.distributelock.mapper.DistributeLockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duanxin
 * @version 1.0
 * @className DemoController
 * @date 2020/05/31 10:28
 */
@RestController
public class DbLockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbLockController.class);

    private Lock lock = new ReentrantLock();

    @Resource
    private DistributeLockMapper distributeLockMapper;

    /**
     * 分布式应用锁(利用数据库行锁实现)
     * 优点：实现简单，易于操作
     * 缺点：当并发量大时，数据库压力大
     * */
    @GetMapping("distributedLock01")
    @Transactional(rollbackFor = Exception.class)
    public String distributedLock01() throws Exception {
        LOGGER.info("我进入方法了");
        DistributeLock distributeLock = distributeLockMapper.selectDistributeLock("demo");
        if (distributeLock == null) {
            throw new Exception("分布式锁获取失败");
        }
        LOGGER.info("我获取到锁了");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "我已经完成了";
    }

    /**
     * 单体应用锁
     * 缺陷：分布式情况下无法保障锁的有效性
     * */
    @GetMapping("singleLock")
    public String singleLock() {
        LOGGER.info("我进入方法了");
        lock.lock();
        LOGGER.info("我获取到锁了");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "我已经完成了";
    }
}
