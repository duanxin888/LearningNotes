package com.daunxin.sort;

import java.util.Arrays;

/**
 * 选择排序
 * 分析结果：选择排序是原地排序算法，不是稳定排序算法
 * 时间复杂度：最坏，最好，平均都是O(n^2)
 * @author duanxin
 * @version 1.0
 * @className SelectionSort
 * @date 2020/05/21 20:18
 */
public class SelectionSort {

    public static void selectionSort(int[] arr, int n) {
        if (n <= 1) {
            return ;
        }
        int minIndex = 0;
        for (int i = 0; i < n - 1; ++i) {
            // 找出最小数
            minIndex = i;
            for (int j = i + 1; j < n; ++j) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 5, 9, 6, 4};
        selectionSort(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }
}
