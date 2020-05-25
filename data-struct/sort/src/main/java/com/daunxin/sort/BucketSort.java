package com.daunxin.sort;

import java.util.Arrays;

/**
 * 桶排序（适用于大数据量的排序）：对一个基数比较大的数据进行排序，
 * 首先对数据分配到一定数量的桶中（桶与桶之间存在天然的大小顺序）
 * 然后对每个桶中的数据进行快速排序，桶与桶之间的数据不需要进行再排序
 * 最后将数据从各个桶中收取
 *
 * 时间复杂度：O(n)，m个桶，n个数据，每个桶中的数据k=n/m，每个桶使用快排时间复杂度O(klogk)
 * m个桶的时间复杂度O(mklogk)，当m无限接近n时，时间复杂度为O(n)
 *
 * 问题：容易形成有些桶中的数据很多，有些桶中的数据很少
 * 极端情况下，数据分配到一个桶中，时间复杂度退化到O(nlogn)
 * @author duanxin
 * @version 1.0
 * @className BucketSort
 * @date 2020/05/25 19:00
 */
public class BucketSort {

    /**
     * 桶排序
     * @param arr 数组
     * @param bucketSize 桶容量
     * @date 2020/5/25 19:09
     */
    public static void bucketSort(int[] arr, int bucketSize) {
        if (arr.length <= 2) {
            return ;
        }
        int minValue = arr[0];
        int maxValue = arr[1];

        // 挑出数组中最大值和最小值
        for (int value : arr) {
            if (minValue > value) {
                minValue = value;
            } else if (maxValue < value) {
                maxValue = value;
            }
        }

        // 桶数量
        int bucketCount = (maxValue - minValue) / bucketSize + 1;
        int[][] buckets = new int[bucketCount][bucketSize];
        int[] indexArr = new int[bucketCount];

        // 将数值分配到各个桶中
        for (int value : arr) {
            // 计算出该数据处于哪个桶中
            int bucketIndex = (value - minValue) / bucketSize;
            if (indexArr[bucketIndex] == buckets[bucketIndex].length) { // 判断桶容量是否要扩容
                ensureCapacity(buckets, bucketIndex);
            }
            buckets[bucketIndex][indexArr[bucketIndex]++] = value;
        }

        // 对每个桶进行快速排序
        int k = 0;
        for (int i = 0; i < bucketCount; ++i) {
            if (indexArr[i] == 0) { // 桶中无数据
                continue;
            }
            quickSortC(buckets[i], 0, indexArr[i] - 1);
            for (int j = 0; j < indexArr[i]; ++j) { // 将桶中的数据放回原数组中
                arr[k++] = buckets[i][j];
            }
        }
    }

    /** 快速排序主体 */
    private static void quickSortC(int[] arr, int p, int r) {
        if (p >= r) {
            return ;
        }

        // 计算分区点
        int q = partition(arr, p, r);
        quickSortC(arr, p, q - 1);
        quickSortC(arr, q + 1, r);
    }

    /** 分区函数 */
    private static int partition(int[] arr, int p, int r) {
        int pivot = arr[r];
        int i = p;
        for (int j = p; j < r; ++j) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }

        swap(arr, i, r);

        return i;
    }

    /** 交换元素 */
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /** 桶扩容 */
    private static void ensureCapacity(int[][] buckets, int bucketIndex) {
        int[] tmpArr = buckets[bucketIndex];
        // 进行2的幂等进行扩容
        int[] newArr = new int[tmpArr.length * 2];
        for (int i = 0, tmpArrLength = tmpArr.length; i < tmpArrLength; i++) {
            newArr[i] = tmpArr[i];
        }
        buckets[bucketIndex] = newArr;
    }

    /** 测试 */
    public static void main(String[] args) {
        int[] arr = {3, 5, 1, 8, 4, 9, 10, 6, 5};
        bucketSort(arr, 3);
        System.out.println(Arrays.toString(arr));
    }

}
