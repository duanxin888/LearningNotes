package com.duanxin.distributelock.task;

import com.duanxin.distributelock.utils.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 使用 redis 分布式锁解决多集群下spring Scheduled任务重复执行的问题
 * @author duanxin
 * @version 1.0
 * @className SendTask
 * @date 2020/06/01 15:32
 */
@Component
public class SendTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendTask.class);

    @Resource
    private RedisLock redisLock;

    //@Scheduled(cron = "0/5 * * * * ?")
    public void sendSms() {
        String key = "redisKey";
        String value = UUID.randomUUID().toString().trim();
        long expireTime = 30L;
        boolean lock = redisLock.lock(key, value, expireTime);
        try {
            if (lock) {
                LOGGER.info("向133XXXXXXXX发送短信");
            }
        }catch (Exception e) {
            LOGGER.error("发送失败，出现异常:{}", e);
        } finally {
            redisLock.unlock(key, value);
        }
    }

}
