package com.daunxin.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 分析结论：冒泡排序是原地排序算法（不需要额外的空间）
 * 是稳定排序算法（对于相同的元素不需要进行交换位置）
 * 时间复杂度：最坏的情况（倒序）O(n^2)，最好的情况（顺序）：O(n)，平均复杂度：O(n^2)
 * @author duanxin
 * @version 1.0
 * @className BubbleSort
 * @date 2020/05/21 19:28
 */
public class BubbleSort {

    // 从小到大进行排序
    public static void bubbleSort(int[] arr, int n) {
        if (n <= 1) {
            return ;
        }
        for (int i = 0; i < n; ++i) {
            // 标志，进行标志序列为顺序序列
            boolean flag = false;
            for (int j = 0; j < n - 1 - i; ++j) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    // 进行测试
    public static void main(String[] args) {
        int[] arr = {3, 2, 5, 1, 7, 1};
        bubbleSort(arr, 6);
        System.out.println(Arrays.toString(arr));
    }

}
