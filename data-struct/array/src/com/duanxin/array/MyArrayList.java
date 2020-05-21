package com.duanxin.array;

/**
 * 1）数组的插入、删除、按下标进行随机访问操作
 * 2）数据类型为泛型
 * 3）支持动态扩充
 * @author duanxin
 * @version 1.0
 * @date 2020/3/22 16:08
 */
public class MyArrayList<T> {

    /**
     * 定义泛型数据data保存数据
     * */
    public Object[] data;

    /**
     * 定义数组的长度
     * */
    private int n;

    /**
     * 定义实际数据个数
     * */
    private int count;

    /**
     * 默认构造函数
     * 数组长度为16
     * */
    public MyArrayList() {
        this.n = 16;
        this.data = new Object[n];
    }

    /**
     * 自定义数组大小
     * */
    public MyArrayList(int n) {
        this.n = n;
        this.data = new Object[n];
    }

    /**
     * 添加数据
     * */
    public boolean insert(int index, T value) {
        // 非法插入数据
        rangeCheckIndex(index);
        // 数值已满，需扩充
        if (n == count) {
            Object[] newData = new Object[2 * n];
            this.n = 2 * n;
            System.arraycopy(data, 0, newData, 0, n);
            data = newData;
        }
        // 进行数据插入
        for (int i = count; i > index; --i) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        count++;
        return true;
    }

    /**
     * 删除数据
     * */
    public boolean delete(int index) {
        // 非法判断
        if (index < 0 || index >= count) {
            return false;
        }
        // 进行数据删除
        for (int i = index + 1; i < count; ++i) {
            data[i - 1] = data[i];
        }
        --count;
        return true;
    }

    /**
     * 根据下标访问数据
     * */
    public T get(int index) {
        rangeCheckIndex(index);
        return elementData(index);
    }

    /**
     * 输出数组全部数据
     * */
    public void printAll() {
        for (int i = 0; i < count; ++i) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /**
     * 下标非法越界判断
     * */
    private void rangeCheckIndex(int index) {
        if (index < 0 || index > n) {
            throw new RuntimeException("下标越界");
        }
    }

    /**
     * 数据转换
     * */
    private T elementData(int index) {
        return (T) data[index];
    }

    /**
     * 测试
     * 实战，Leetcode T88
     * */
    public static void main(String[] args) {
        MyArrayList<Integer> array = new MyArrayList<>();
        array.printAll();
        array.insert(0, 12);
        array.insert(1, 1);
        array.insert(2, 2);
        array.insert(0, 3);
        array.insert(3, 4);
        array.insert(3, 5);
        array.printAll();
        System.out.println("----------------------");
        array.delete(6);
        array.printAll();
    }
}
