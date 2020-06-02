package com.duanxin.hash;

import java.util.HashMap;

/**
 * 利用 HashMap 实现LRU算法
 * 实质上 LinkedHashMap 也可以实现LRU算法
 * 添加、删除、获取的时间复杂度都是 O(1)
 * @author duanxin
 * @version 1.0
 * @className LRUBaseHashMap
 * @date 2020/06/02 20:48
 */
public class LRUBaseHashMap<K, V> {

    /** 链表默认容量 */
    private static final int DEFAULT_CAPACITY = 10;

    /** 链表长度 */
    private int length;

    /** 链表容量 */
    private int capacity;

    /** 头结点 */
    private DNode<K, V> headNode;

    /** 尾结点 */
    private DNode<K, V> tailNode;

    /** 散列表存储Key */
    private HashMap<K, DNode<K, V>> table;

    static class DNode<K, V> {

        /** 键 */
        private K key;

        /** 值 */
        private V value;

        /** 前驱结点 */
        private DNode<K, V> prev;

        /** 后驱结点 */
        private DNode<K, V> next;

        DNode() {

        }

        DNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUBaseHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public LRUBaseHashMap(int capacity) {
        this.capacity = capacity;
        length = 0;
        headNode = new DNode<>();
        tailNode = new DNode<>();

        headNode.next = tailNode;
        tailNode.prev = headNode;

        table = new HashMap<>();
    }

    /**
     * 添加
     * @param key 键
     * @param value 值
     * @date 2020/6/2 20:57
     */
    public void add(K key, V value) {
        DNode<K, V> node = table.get(key);
        if (node == null) { // 不存在该节点，进行添加
            DNode<K, V> newNode = new DNode<>(key, value);
            table.put(key, newNode);
            addNode(newNode);

            if (++length > capacity) {
                DNode<K, V> tail = popTail();
                table.remove(tail.key);
                length--;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }

    /**
     * 将添加结点添加到 LUR 队列头部结点
     * @param node 结点
     * @date 2020/6/2 21:12
     */
    private void moveToHead(DNode<K, V> node) {
        removeNode(node);
        addNode(node);
    }

    /**
     * 当队列满了，移除队尾结点
     * @date 2020/6/2 21:12
     * @return com.duanxin.hash.LRUBaseHashMap.DNode<K,V>
     */
    private DNode<K, V> popTail() {
        DNode<K, V> node = tailNode.prev;
        removeNode(node);
        return node;
    }

    /**
     * 移除队列中的结点
     * @param node 结点
     * @date 2020/6/2 21:48
     */
    private void removeNode(DNode<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * 向队列添加新节点
     * @param newNode 新结点
     * @date 2020/6/2 21:13
     */
    private void addNode(DNode<K, V> newNode) {
        newNode.next = headNode.next;
        headNode.next.prev = newNode;
        headNode.next = newNode;
        newNode.prev = headNode;
    }

    /**
     * 获取
     * @param key 键
     * @date 2020/6/2 20:58
     * @return V
     */
    public V get(K key) {
        DNode<K, V> node = table.get(key);
        if (node == null) {
            return null;
        }
        moveToHead(node);
        return node.value;
    }

    /**
     * 移除
     * @param key 键
     * @date 2020/6/2 20:58
     * @return V
     */
    public V remove(K key) {
        DNode<K, V> node = table.remove(key);;
        if (node == null) {
            return null;
        }
        removeNode(node);
        length--;
        return node.value;
    }

    /**
     * 输出所有数据
     * @date 2020/6/2 20:59
     */
    private void printAll() {
        DNode<K, V> head = headNode;
        do {
            head = head.next;
            System.out.print(head.value + ",");
        } while (head.next != tailNode);
        System.out.println();
    }

    /** 测试 */
    public static void main(String[] args) {
        LRUBaseHashMap<String, String> map = new LRUBaseHashMap<>();
        map.add("name1", "aaa");
        map.add("name2", "bbb");
        map.add("name3", "ccc");
        map.add("name4", "ddd");
        map.add("name5", "eee");
        map.printAll();
        map.get("name1");
        map.printAll();
        map.add("name6", "fff");
        map.printAll();
        map.remove("name6");
        map.printAll();
    }
}
