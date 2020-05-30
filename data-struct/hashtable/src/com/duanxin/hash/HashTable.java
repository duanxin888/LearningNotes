package com.duanxin.hash;

import java.io.Serializable;

/**
 * 散列表
 *
 * 思考题：Word文档中单词拼写检查功能是如何实现的？
 * 解题：常用单词20万，每个单词平均长度10个字母，平均每个单词占10个字节，
 * 20万单词占2MB存储空间，假设在放大10倍20MB，我们可以利用散列表存放这些单词。
 *
 * @author duanxin
 * @version 1.0
 * @className HashTable
 * @date 2020/05/27 20:52
 */
public class HashTable<K, V> implements Serializable {
    private static final long serialVersionUID = -5233614724599412401L;

    /** 散列表默认长度 */
    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    /** 装载因子 */
    private static final float LOAD_FLOAT = 0.75f;

    /** 初始化散列表数组 */
    private Entry<K, V>[] table;

    /** 实际元素个数 */
    private int size = 0;

    /** 散列表索引使用数量 */
    private int use = 0;

    public HashTable() {
        table = (Entry<K, V>[]) new Entry[DEFAULT_INITIAL_CAPACITY];
    }

    static class Entry<K, V> {
        K key;
        V value;

        Entry<K, V> next;
        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * 添加
     * @param key 键
     * @param value 值
     * @date 2020/5/27 21:02
     */
    public void put(K key, V value) {
        int index = hash(key);
        // 位置未被引用，创建哨兵节点
        if (table[index] == null) {
            table[index] = new Entry<>(null, null, null);
        }

        Entry<K, V> tmp = table[index];
        if (tmp.next == null) { // 新增结点
            tmp.next = new Entry<>(key, value, null);
            size++;
            use++;
            if (use >= table.length * LOAD_FLOAT) { // 动态扩容
                resize();
            }
        } else { // 解决散列冲突，使用链表法
            do {
                tmp = tmp.next;
                if (tmp.key == key) { // 进行值覆盖
                    tmp.value = value;
                    return ;
                }
            } while(tmp.next != null);

            // 将 key 放到链表头部
            Entry<K, V> temp = table[index].next;
            table[index].next = new Entry<>(key, value, temp);
            size++;
        }
    }

    /**
     * 对hash表进行扩容
     * @date 2020/5/30 19:35
     */
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = (Entry<K, V>[]) new Entry[oldTable.length * 2];
        for (Entry<K, V> entry : oldTable) {
            if (entry == null || entry.next == null) { // 节点无数据
                continue;
            }
            Entry<K, V> e = entry;
            while (e.next != null) {
                e = e.next;
                // 重新计算下标值
                int index = hash(e.key);
                if (table[index] == null) { // 创建哨兵结点
                    use++;
                    table[index] = new Entry<>(null, null, null);
                }
                table[index].next = new Entry<>(e.key, e.value, table[index].next);
            }
        }
    }

    /**
     * 计算hash值
     * @param key 键
     * @date 2020/5/30 19:26
     * @return int
     */
    private int hash(Object key) {
        int h = 0;
        return (key == null) ? 0 : ((h = key.hashCode()) ^ (h >>> 16)) % table.length;
    }

    /**
     * 删除
     * @param key 键
     * @date 2020/5/27 21:03
     */
    public void remove(K key) {
        int index = hash(key);
        Entry<K, V> e = table[index];
        if (e == null || e.next == null) {
            return ;
        }

        Entry<K, V> pre;
        Entry<K, V> headNode = table[index];
        do {
            pre = e;
            e = e.next;
            if (e.key == key) {
                pre.next = e.next;
                size--;
                if (headNode.next == null) {
                    use--;
                }
                return ;
            }
        } while (e.next != null);
    }

    /**
     * 获取
     * @param key 键
     * @date 2020/5/27 21:03
     * @return V
     */
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> e = table[index];
        if (e == null || e.next == null) {
            return null;
        }
        while (e.next != null) {
            e = e.next;
            if (e.key == key) {
                return e.value;
            }
        }
        return null;
    }

    /** 测试 */
    public static void main(String[] args) {
        HashTable<String, String> table = new HashTable<>();
        table.put("name", "zhangsan");
        table.put("age", "13");

        // 获取值
        System.out.println(table.get("name"));
        System.out.println(table.get("age"));
        System.out.println(table.get("sex"));

        // 删除值
        table.remove("age");
        System.out.println(table.get("name"));
        System.out.println(table.get("age"));
    }

}
