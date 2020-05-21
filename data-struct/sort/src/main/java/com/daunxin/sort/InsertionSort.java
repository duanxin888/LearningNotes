package com.daunxin.sort;

import java.util.Arrays;

/**
 * 插入排序
 * 分析结果：插入排序是原地排序算法，是稳定排序算法（空间复杂度为O(1)）
 * 时间复杂度：最坏的情况：O(n^2), 最好的的情况：O(n), 平均：O(n^2)
 * @author duanxin
 * @version 1.0
 * @className InsertionSort
 * @date 2020/05/21 19:45
 */
public class InsertionSort {

    // 从小到大进行排序
    public static void insertionSort(int[] arr, int n) {
        if (n <= 1) {
            return ;
        }
        for (int i = 1; i < n; ++i) {
            int val = arr[i];
            int j = i - 1;
            for (; j >= 0; --j) {
                if (arr[j] > val) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = val;
        }
    }

    // 进行测试
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 7, 8, 5, 1};
        insertionSort(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }
}
