package com.duanxin.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock map util
 *
 * @author duanxin
 * @version 1.0
 * @className LockMapUtils
 * @date 2020/08/31 14:33
 */
public class LockMapUtils {

    private static final ConcurrentHashMap<String, Lock> lockMap = new ConcurrentHashMap<>();

    private LockMapUtils() {

    }

    public static Lock get(String key) {
        return getIfAbsent(key);
    }

    public static void remove(String key) {
        lockMap.remove(key);
    }

    private static Lock getIfAbsent(String key) {
        if (!lockMap.containsKey(key)) {
            Lock lock = new ReentrantLock();
            lockMap.put(key, lock);
            return lock;
        }
        return lockMap.get(key);
    }
}
