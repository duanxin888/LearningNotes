package com.duanxin.linkedlist;

/**
 * 1）双向链表
 * 2）实现增、删、查找操作
 * 3）进行 int 数据测试
 * @author duanxin
 * @version 1.0
 * @date 2020/3/23 9:06
 */
public class MyLinkedList<T> {

    /**
     * 定义链表实际数据个数
     * */
    private int size;

    /**
     * 定义链表的头结点
     * */
    private Node<T> first;

    /**
     * 定义链表的尾结点
     * */
    private Node<T> last;

    /**
     * 创建空链表
     * */
    public MyLinkedList() {

    }

    /**
     * 添加头结点
     * */
    private void linkFirst(T t) {
        final Node<T> f = this.first;
        final Node<T> newNode = new Node<>(null, t, f);
        this.first = newNode;
        if (f == null) { // 链表为空
            last = newNode;
        } else { // 链表不为空
            f.prev = newNode;
        }
        ++size;
    }

    /**
     * 添加尾结点
     * */
    private void linkLast(T t) {
        final Node<T> l = this.last;
        Node<T> newNode = new Node<>(l, t, null);
        this.last = newNode;
        if (l == null) { // 链表为空
            first = newNode;
        } else { // 链表不为空
            l.next = newNode;
        }
        ++size;
    }

    /**
     * 为结点 succ 添加前驱结点
     * */
    public void linkBefore(T t, Node<T> succ) {
        Node<T> pred = succ.prev;
        Node<T> newNode = new Node<>(pred, t, succ);
        succ.prev = newNode;
        if (pred == null) { // succ为头结点
            this.first = newNode;
        } else {
            pred.next = newNode;
        }
        ++size;
    }

    /**
     * 删除头结点 f
     * */
    private T unlinkFirst(Node<T> f) {
        final T element = f.item;
        final Node<T> next = f.next;
        f.item = null;
        f.next = null; // gc
        this.first = next;
        if (next == null) { // 链表为空
            this.last = null;
        } else {
            next.prev = null;
        }
        --size;
        return element;
    }

    /**
     * 删除尾节点 l
     * */
    private T unlinkLast(Node<T> l) {
        final T element = l.item;
        final Node<T> prev = l.prev;
        l.item = null;
        l.prev = null; // gc
        this.last = prev;
        if (prev == null) { // 链表为空
            this.first = null;
        } else {
            prev.next = null;
        }
        --size;
        return element;
    }

    /**
     * 删除结点 n
     * */
    public T unlink(Node<T> n) {
        final T element = n.item;
        final Node<T> prev = n.prev;
        final Node<T> next = n.next;

        if (prev == null) { // n 为头结点
            this.first = next;
        } else {
            prev.next = next;
            n.next = null;
        }

        if (next == null) { // n 为尾节点
            this.last = prev;
        } else {
            next.prev = prev;
            n.prev = null;
        }

        n.item = null; // gc
        --size;
        return element;
    }

    /**
     * 获取头结点数据
     * */
    public T getFirst() {
        final Node<T> f = this.first;
        if (f == null) {
            throw new RuntimeException("链表为空");
        }
        return f.item;
    }

    /**
     * 获取尾结点数据
     * */
    public T getLast() {
        final Node<T> l = this.last;
        if (l == null) {
            throw new RuntimeException("链表为空");
        }
        return l.item;
    }

    /**
     * 删除头结点
     * */
    public T removeFirst() {
        final Node<T> f = this.first;
        if (f == null) {
            throw new RuntimeException("链表为空");
        }
        return unlinkFirst(f);
    }

    /**
     * 删除尾节点
     * */
    public T removeLast() {
        final Node<T> l = this.last;
        if (l == null) {
            throw new RuntimeException("链表为空");
        }
        return unlinkLast(l);
    }

    /**
     * 添加头结点
     * */
    public void addFirst(T t) {
        linkFirst(t);
    }

    /**
     * 添加尾结点
     * */
    public void addLast(T t) {
        linkLast(t);
    }

    /**
     * 返回链表元素个数
     * */
    public int size() {
        return this.size;
    }

    /**
     * 移除指定元素 o 第一出现的位置
     * */
    public boolean remove(Object o) {
        if (o == null) { // 该元素为空
            for (Node<T> n = this.first; n != null; n = n.next) {
                if (n.item == null) {
                    unlink(n);
                    return true;
                }
            }
        } else {
            for (Node<T> n = this.first; n != null; n = n.next) {
                if (o.equals(n.item)) {
                    unlink(n);
                    return true;
                }
            }
        }
        // 不存在该元素
        return false;
    }

    /**
     * 清空链表
     * */
    public void clear() {
        for(Node<T> n = this.first; n != null;) {
            Node<T> next = n.next;
            n.item = null;
            n.prev = null;
            n.next = null;
            n = next;
        }

        first = last = null;
        size = 0;
    }

    /**
     * 根据下标获取元素
     * */
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    /**
     * 更新下标为 index 的结点元素为 element
     * */
    public T set(int index, T element) {
        checkElementIndex(index);
        Node<T> node = node(index);
        T oldElem = node.item;
        node.item = element;
        return oldElem;
    }

    /**
     * 在下标为 index 处添加新结点
     * */
    public void add(T element, int index) {
        if (index < 0 || index > size) {
            throw new RuntimeException("Index: "+index+", Size: "+size);
        }
        if (index == size) {
            linkLast(element);
        } else {
            linkBefore(element, node(index));
        }
    }

    /**
     * 打印全部结点元素
     * */
    public void printAll() {
        for (Node<T> n = this.first; n != null; n = n.next) {
            System.out.print(n.item + " ");
        }
        System.out.println();
    }

    /**
     * 返回下标为 index 的结点
     * */
    Node<T> node(int index) {
        if (index > (size >> 1)) { // 该结点在链表后半段
            Node<T> n = this.last;
            for (int i = size - 1; i > index; --i) {
                n = n.prev;
            }
            return n;
        } else { // 该结点在链表前半段
            Node<T> n = this.first;
            for (int i = 0; i < index; ++i) {
                n = n.next;
            }
            return n;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * 定义结点
     * */
    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    /**
     * 进行测试
     * */
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addFirst(5);
        list.addFirst(6);
        list.printAll();
        System.out.println("---------------------");
        list.addLast(0);
        list.addLast(9);
        list.addLast(8);
        list.addLast(7);
        list.printAll();
        System.out.println("---------------------");
        list.remove(1);
        list.remove(3);
        list.remove(2);
        list.printAll();
        System.out.println("---------------------");
        list.removeLast();
        list.removeLast();
        list.removeFirst();
        list.removeFirst();
        list.printAll();
        System.out.println(list.getLast());
        System.out.println(list.getFirst());
        System.out.println(list.get(2));
        System.out.println("---------------------");
        list.add(1, 1);
        list.add(2, 2);
        list.add(3, 3);
        list.add(11, 4);
        list.printAll();
        System.out.println("---------------------");
        list.set(1, 11);
        list.set(2, 12);
        list.set(3, 13);
        list.set(4, 14);
        list.printAll();
    }
}
