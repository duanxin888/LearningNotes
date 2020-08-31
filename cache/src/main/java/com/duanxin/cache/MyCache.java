package com.duanxin.cache;

import lombok.Getter;
import lombok.Setter;

/**
 * 缓存实体类
 *
 * MyCache:
 *      1、线程安全
 *      2、设置过期删除策略（主动删除 + 惰性删除）
 *
 * @author duanxin
 * @version 1.0
 * @className MyCache
 * @date 2020/08/29 10:10
 */
@Setter
@Getter
public class MyCache implements Comparable<MyCache>{

    /** 缓存键 */
    private Object key;

    /** 缓存值 */
    private Object value;

    /** 最后一次访问时间 */
    private Long lastTime;

    /** 创建时间 */
    private Long writeTime;

    /** 过期时间 */
    private Long expiredTime;

    /** 命中次数 */
    private Integer hitCount;

    @Override
    public int compareTo(MyCache o) {
        return hitCount.compareTo(o.hitCount);
    }
}
