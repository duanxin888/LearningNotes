package com.daunxin.sort;

import java.util.Arrays;

/**
 * 快速排序
 * 结果分析：快速排序不是稳定排序算法，是原地排序算法
 * 时间复杂度：O(nlogn)   空间复杂度：O(1)
 * @author duanxin
 * @version 1.0
 * @className QuickSort
 * @date 2020/05/24 20:18
 */
public class QuickSort {

    // arr 是数组，n 是数组元素大小
    public static void quickSort(int[] arr, int n) {
        quickSortInternally(arr, 0, n - 1);
    }

    // 快速排序递归函数，p，r为下标
    private static void quickSortInternally(int[] arr, int p, int r) {
        // 递归结束条件
        if (p >= r) {
            return;
        }

        // 计算分区点
        int q = partition(arr, p, r);
        quickSortInternally(arr, p, q - 1);
        quickSortInternally(arr, q + 1, r);
    }

    private static int partition(int[] arr, int p, int r) {
        int pivot = arr[r];
        int i = p; // 交换元素下标
        for (int j = p; j < r; ++j) {
            if (arr[j] < pivot) {
                if (i == j) {
                    ++i;
                } else {
                    int tmp = arr[i];
                    arr[i++] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        int tmp = arr[i];
        arr[i] = arr[r];
        arr[r] = tmp;
        return i;
    }

    // 测试
    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 7, 5, 9, 3};
        quickSort(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }

}
