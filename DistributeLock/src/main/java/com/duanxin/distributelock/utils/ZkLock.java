package com.duanxin.distributelock.utils;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @className ZkLock
 * @date 2020/06/01 16:15
 */
public class ZkLock implements Watcher, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkLock.class);

    private ZooKeeper zookeeper;

    private String zNode;

    public ZkLock() throws IOException {
        // param1：连接参数，Zookeeper地址与端口号
        // param2：连接超时时间
        // param3：Watcher
        zookeeper = new ZooKeeper("192.168.100.160:2181",
                30000,
                this);
    }

    /**
     * 获取分布式锁
     * @param businessCode 业务标识
     * @date 2020/6/1 16:19
     * @return boolean
     */
    public boolean lock(String businessCode) {
        String path = "/" + businessCode;
        try {
            // 判断业务根节点是否存在
            Stat stat = zookeeper.exists(path, false);
            if (stat == null) { // 不存在，进行创建
                zookeeper.create(path, // 节点名称
                        businessCode.getBytes(), // 节点初始数据
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, // 节点的权限：全权限
                        CreateMode.PERSISTENT); // 节点的类型：持久节点
            }

            // 创建瞬时有序子节点 /order/order_0000000001
            zNode = zookeeper.create(path + path + "_", // 节点名称
                    businessCode.getBytes(), // 节点初始数据
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, // 节点的权限：全权限
                    CreateMode.EPHEMERAL_SEQUENTIAL);// 节点的类型：持久节点
            // 获取所有子节点
            List<String> childrenNodes = zookeeper.getChildren(path, false);
            // 子节点进行排序
            Collections.sort(childrenNodes);
            // 获取第一个节点：order_0000000001
            String firstNode = childrenNodes.get(0);
            if (zNode.endsWith(firstNode)) { // 判断该节点是第一个节点
                return true;
            }

            // 不是第一个节点，监听前一个节点是否消失
            String preNode = firstNode;
            for(String node : childrenNodes) {
                if (zNode.endsWith(node)) {
                    zookeeper.exists(path + "/" + preNode, true);
                    break;
                } else {
                    preNode = node;
                }
            }

            // 线程等待前一个节点消失
            synchronized (this) {
                wait();
            }
            return true;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 释放分布式锁
     * @date 2020/6/1 16:47
     */
    public void unlock() throws Exception {
        // 删除节点
        zookeeper.delete(zNode, -1);
        zookeeper.close();
        LOGGER.info("释放锁成功！！");
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        // 监听到节点删除，唤醒线程
        if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
            synchronized (this) {
                notify();
            }
        }
    }

    @Override
    public void close() throws Exception {
        unlock();
    }
}
