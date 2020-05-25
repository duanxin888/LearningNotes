package com.daunxin.sort;

import java.util.Arrays;

/**
 * 计数排序：
 * 例子：考生的满分是900分，最小是0分，这个数据的范围很小，所以我们可以分成901个桶，对应分数从0分到900分。
 * 根据考生的成绩，我们将这50万考生划分到这901个桶里。桶内的数据都是分数相同的考生，所以并不需要再进行排序。
 * 我们只需要依次扫描每个桶，将桶内的考生依次输出到一个数组中，就实现了50万考生的排序。因为只涉及扫描遍历操作，
 * 所以时间复杂度是O(n)。
 *
 * 总结：计数排序只适用于数据量不大的场景，如果数据范围k比要排序的的数据n大很多，就不适合计数排序。
 * 计数排序只能给非负整数排序，如果要排序的数据是其他类型的，要将其在不改变相对大小的情况下，转化为非负整数。
 * @author duanxin
 * @version 1.0
 * @className CountingSort
 * @date 2020/05/25 20:10
 */
public class CountingSort {

    /**
     * 计数排序，假设数组中的数据都是非负数
     * @param arr 数组
     * @param n 数组数据大小
     * @date 2020/5/25 20:14
     */
    public static void countingSort(int[] arr, int n) {
        if (n <= 1) {
            return ;
        }

        int max = 0;
        for (int i = 0; i < n; ++i) { // 求出最大值
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // 创建一个计数数组c，下标大小[0, max]
        int[] c = new int[max + 1];

        // 计算出每个元素的个数，并放入c中
        for (int i = 0; i < n; ++i) {
            c[arr[i]]++;
        }

        // 依次累计
        for (int i = 1; i < max + 1; ++i) {
            c[i] = c[i] + c[i - 1];
        }

        // 创建辅助数组r，下标为[0, n]
        int[] r = new int[n];
        for (int i = 0; i < n; ++i) {
            // 计算出 arr[i] 在 r 中排序后的位置
            int index = c[arr[i]] - 1;
            r[index] = arr[i];
            c[arr[i]]--;
        }

        // 结果进行拷贝
        System.arraycopy(r, 0, arr, 0, n);
    }

    /** 测试 */
    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 8, 5, 9, 3};
        countingSort(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }

}
