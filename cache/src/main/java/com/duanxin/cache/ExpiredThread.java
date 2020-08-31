package com.duanxin.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * expired cache check thread
 *
 * @author duanxin
 * @version 1.0
 * @className ExpiredThread
 * @date 2020/08/29 10:26
 */
@Slf4j
public class ExpiredThread implements Runnable{
    @Override
    public void run() {
        while (true) {
            try {
                // check every ten seconds
                TimeUnit.SECONDS.sleep(10);
                // cache check and clear
                expireCache();
            } catch (Exception ex) {
                log.warn("failed to execute expired task", ex);
            }
        }
    }

    /**
     * cache check and clear
     * */
    private void expireCache() {
        log.info("begin to check expired cache");
        CacheGlobal.cacheMap.keySet().forEach(key -> {
            MyCache myCache = CacheGlobal.cacheMap.get(key);
            // startTime - nowTime
            Lock lock = LockMapUtils.get(key);
            try {
                lock.lock();
                long timeout = System.currentTimeMillis() - myCache.getWriteTime();
                if (myCache.getExpiredTime() < timeout) {
                    // expired and remove
                    CacheGlobal.cacheMap.remove(key);
                    LockMapUtils.remove(key);
                }
            } finally {
                lock.unlock();
            }
        });
    }
}
