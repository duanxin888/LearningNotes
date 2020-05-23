package com.daunxin.sort;

import java.util.Arrays;

/**
 * 归并排序
 * 结果分析：归并排序是稳定排序算法（根据代码来看），不是原地排序算法（需要辅助空间）
 * 时间复杂度：O(nlogn), 空间复杂度：O(n)，最终需要一个n大小的数组进行数据转换，辅助数组是个局部变量
 * @author duanxin
 * @version 1.0
 * @className MergeSort
 * @date 2020/05/23 20:20
 */
public class MergeSort {

    // 归并排序主体，arr是数组，n表示数组大小
    public static void mergeSort(int[] arr, int n) {
        mergeSortInternally(arr, 0, n - 1);
    }

    // 递归调用函数
    private static void mergeSortInternally(int[] arr, int p, int r) {
        if (p >= r) {
            return ;
        }

        int q = p + (r - p) / 2;

        // 归并递归调用
        mergeSortInternally(arr, p, q);
        mergeSortInternally(arr, q + 1, r);

        // 合并操作，将arr[p ... q] 和 arr[q+1 ... r] 合并为 arr[p ... r]
        merge(arr, p, q, r);
    }

    // 合并
    private static void merge(int[] arr, int p, int q, int r) {
        int i = p;
        int j = q + 1;
        int k = 0;
        int[] tmp = new int[r - p + 1]; // 申请一个和 arr[p ... r]一样大小的数组

        while(i <= q && j <= r) {
            if (arr[i] <= arr[j]) {
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
            }
        }

        // 判断哪个小数组中是否有剩余的元素
        int start = i;
        int end = q;
        if (j <= r) {
            start = j;
            end = r;
        }

        // 拷贝剩余元素
        while(start <= end) {
            tmp[k++] = arr[start++];
        }

        // 将tmp的数据拷贝到原始数组中
        for (i = 0; i <= r - p; ++i) {
            arr[p + i] = tmp[i];
        }
    }

    // 进行测试
    public static void main(String[] args) {
        int[] arr = {3, 2, 5, 9, 4, 1, 7};
        mergeSort(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }
}
