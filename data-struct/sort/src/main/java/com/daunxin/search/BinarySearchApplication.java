package com.daunxin.search;

/**
 * 二分查找的一些变形应用：当 mid 对应元素符合条件时需要进行符合条件的判断
 * @author duanxin
 * @version 1.0
 * @className BinarySearchApplication
 * @date 2020/05/26 20:34
 */
public class BinarySearchApplication {

    /**
     * 查找出第一个等于 val 的元素
     * @param arr 数组
     * @param n 数组数据量
     * @param val 目标值
     * @date 2020/5/26 20:35
     * @return int
     */
    public static int binarySearchApplication01(int[] arr, int n, int val) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + ((high - low) >>> 1);

            if (val < arr[mid]) { // val 在靠左边的位置
                high = mid - 1;
            } else if (val > arr[mid]) { // val 在靠右边的位置
                low = mid + 1;
            } else {
                // mid 是第一个元素或者 mid 前一个元素不等于 val
                if (mid == 0 || arr[mid - 1] != val) {
                    return mid;
                } else { // 指定元素在 low 与 mid 之间
                    high = mid - 1;
                }
            }
        }

        return -(low + 1);
    }

    /**
     * 查找出最后一个等于 val 的元素
     * @param arr 数组
     * @param n 数组数据量
     * @param val 目标值
     * @date 2020/5/26 20:42
     * @return int
     */
    public static int binarySearchApplication02(int[] arr, int n, int val) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + ((high - low) >>> 1);
            if (val < arr[mid]) { // val 在靠左边的位置
                high = mid - 1;
            } else if (val > arr[mid]) { // val 在靠右边的位置
                low = mid + 1;
            } else {
                // mid 是最后一个元素或 mid 后边一个元素值不等于 val
                if (mid == n - 1 || arr[mid + 1] != val) {
                    return mid;
                } else { // 指定元素在 mid 与 high 之间
                    low = mid + 1;
                }
            }
        }

        return -(low + 1);
    }

    /**
     * 查找第一个大于等于给定值的元素
     * @param arr 数组
     * @param n 数组数据量
     * @param val 目标值
     * @date 2020/5/26 20:51
     * @return int
     */
    public static int binarySearchApplication03(int[] arr, int n, int val) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + ((high - low) >>> 1);
            if (arr[mid] >= val) {
                // mid 是第一个元素或者 mid 前一个元素小于 val
                if (mid == 0 || arr[mid - 1] < val) {
                    return mid;
                } else { // 还有更小的值在 low 与 mid 之间
                    high = mid - 1;
                }
            } else { // mid 元素过小
                low = mid + 1;
            }
        }

        return -(low + 1);
    }

    /**
     * 查找最后一个小于等于给定值的元素
     * @param arr 数组
     * @param n 数组数据量
     * @param val 目标值
     * @date 2020/5/26 21:05
     * @return int
     */
    public static int binarySearchApplication04(int[] arr, int n, int val) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + ((high - low) >>> 1);
            if (arr[mid] <= val) {
                if (mid == n - 1 || arr[mid + 1] > val) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            } else {
                high = mid - 1;
            }
        }

        return -(low + 1);
    }

    /** 测试 */
    public static void main(String[] args) {
        int[] arr = {1, 4, 6, 7, 8, 8, 8, 11};
        // 查找出第一个等于给定值的元素
        System.out.println(binarySearchApplication01(arr, arr.length, 8));
        // 查找出最后一个等于给定值的元素
        System.out.println(binarySearchApplication02(arr, arr.length, 4));
        // 查找第一个大于等于给定值的元素
        System.out.println(binarySearchApplication03(arr, arr.length, 9));
        // 查找最后一个小于等于给定值的元素
        System.out.println(binarySearchApplication04(arr, arr.length, 12));
    }
}
