package com.daunxin.search;

/**
 * 二分查找：该算法针对的是一个有序的数据集合，查找思想类似于分治思想。
 * 每次都通过跟区间的中间元素对比，将待查找的区间缩小为之前的一半，
 * 直到找到要查找的元素，或者区间被缩小为0
 *
 * 时间复杂度：O(logn)，有时候比O(1)算法节省更多时间
 *
 * 局限性：
 *      1、依赖于数组
 *      2、只适用于有序数据集
 *      3、数据量太小与数据量太大都不适用于二分查找
 * @author duanxin
 * @version 1.0
 * @className BinarySearch
 * @date 2020/05/26 19:41
 */
public class BinarySearch {

    /**
     * 非递归二分查找函数
     * @param arr 数组
     * @param n 数组数据量
     * @param val 查找的值
     * @date 2020/5/26 19:47
     * @return int
     */
    public static int binarySearch(int[] arr, int n, int val) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + ((high - low) >>> 1); // 提高效率，防止数据溢出

            if (val == arr[mid]) {
                return mid;
            } else if (val < arr[mid]){ // val 在靠左边位置
                high = mid - 1;
            } else { // val 在靠右边位置
                low = mid + 1;
            }
        }

        return -(low + 1);
    }

    /**
     * 递归二分查找
     * @param arr 数组
     * @param n 数组数据量
     * @param val 查找的值
     * @date 2020/5/26 19:53
     * @return int
     */
    public static int binarySearchForRecursive(int[] arr, int n, int val) {
        return bsearchInternally(arr, 0, n - 1, val);
    }

    private static int bsearchInternally(int[] arr, int low, int high, int val) {
        if (low > high) {
            return -(low + 1);
        }
        int mid = low + ((high - low) >>> 1);

        if (arr[mid] == val) {
            return mid;
        } else if (val < arr[mid]){ // val 在靠左边的位置
            return bsearchInternally(arr, low, mid - 1, val);
        } else { // val 在靠右边的位置
            return bsearchInternally(arr, mid + 1, high, val);
        }
    }

    /** 测试 */
    public static void main(String[] args) {
        int[] arr = {1, 4, 6, 7, 9, 11, 19};
        // 非递归二分查找
        System.out.println(binarySearch(arr, arr.length, 5));
        // 递归二分查找
        System.out.println(binarySearchForRecursive(arr, arr.length, 19));
    }

}
