package com.daunxin.sort;

import java.util.Arrays;

/**
 * 堆是一种完全二叉树，每个节点的值大于等于（或小于等于）其子节点的值，堆被分成大顶堆和小顶堆。
 * 堆排序：堆排序包含两个过程，建堆和排序。首先将下标为 n / 2 到 1 的节点，依次进行从上到下的堆化操作，
 * 然后就可以在数组中的数据就形成堆这种数据结构，然后，将堆顶元素放到堆的末尾，并将堆的大小减一，
 * 然后在堆化，重复这个过程，直到堆中元素个数为一，至此完成堆的排序。
 *
 * 在实际开发中，为什么快速排序要比堆排序性能要好？
 *      1、堆排序访问数据的方式没有快速排序友好。对于快速排序来说，访问数据是顺序访问。
 *      而堆排序在访问数据时，是跳跃式访问。在堆排序中，最重要的一步是堆化，
 *      其是按一定的跳跃规则来访问数据，所以这样对于cpu来说不是很友好。
 *      2、对于同样的数据，在排序过程中，堆排序算法的数据交换次数要多于快速排序算法。
 *      堆排序的第一步是进行建堆，建堆的过程是要打乱数据原有的先后顺序，导致原数据的有序性降低。
 *      比如，对于一组有序的数据来说，经过建堆数据反而是无序的。
 *
 * 堆排序的应用：
 *      1、优先级队列：数据出队顺序不是先进先出，而是按照优先级来，优先级最高的，最先出队。
 *      使用大顶堆，出队取出堆顶元素。而优先级队列的具体应用：
 *          1）合并有序小文件：我们将从小文件中取出来的字符串放入到小顶堆中，那堆顶的元素，
 *          也就是优先级队列队首的元素，就是最小的字符串。我们将这个字符串放入到大文件中，
 *          并将其从堆中删除。然后再从小文件中取出下一个字符串，放入到堆中。循环这个过程，
 *          就可以将100个小文件中的数据依次放入到大文件中。
 *          2）高性能定时器：我们按照任务设定的执行时间，将这些任务存储在优先级队列中，
 *          队列首部（也就是小顶堆的堆顶）存储的是最先执行的任务。
 *      2、求 Top K：首先维护一个大小为 K 的小顶堆，顺序遍历数组，从数组中取出数据与堆顶元素进行
 *      比较。如果比堆顶元素大，把堆顶元素删除，插入该元素；如果比堆顶元素小，则不作处理遍历下一个
 *      元素。
 *      3、求中位数：一个大小为n的有序数组数据从小到大进行排序，如果 n 为奇数，那么前 n / 2 + 1个
 *      元素存在大顶堆，后 n / 2 个元素存在小顶堆，然后中位数是大顶堆堆顶元素；如果 n 为偶数，那么
 *      前 n / 2 个元素放在大顶堆，后 n / 2 个元素存在小顶堆，然后中位数是两个堆顶元素。比如
 *      求 “如何快速求接口的99%响应时间？“ 问题，也是采用类似的思路。
 *
 * @author duanxin
 * @version 1.0
 * @className HeapSort
 * @date 2020/06/10 20:36
 */
public class HeapSort {

    public static void heapSort(int[] arr) {
        if (arr.length <= 1) {
            return ;
        }

        // 建堆
        buildHeap(arr);

        // 排序
        int k = arr.length - 1;
        while (k > 0) {
            // 将堆顶元素和堆末元素交换位置
            swap(arr, 0, k);
            // 剩下元素进行堆化
            heapify(arr, --k, 0);
        }
    }

    /**
     * 建堆
     * @param arr 数组
     * @date 2020/6/10 20:48
     */
    private static void buildHeap(int[] arr) {
        // 从 (arr.length - 1) / 2 为最后一个叶子节点的父节点
        // 也就是从最后一个叶子节点，依次堆化直至根节点
        for (int i = (arr.length - 1) / 2; i >= 0; --i) {
            heapify(arr, arr.length - 1, i);
        }
    }

    /**
     * 堆化
     * @param arr 数组
     * @param n 最后堆元素的下标
     * @param i 要堆化的元素下标
     * @date 2020/6/10 20:50
     */
    private static void heapify(int[] arr, int n, int i) {
        while (true) {
            // 最大值位置
            int maxPos = i;
            // 与左子节点(i * 2 + 1)进行比较，获取最大值位置
            if ((i * 2 + 1) <= n && arr[i] < arr[i * 2 + 1]) {
                maxPos = i * 2 + 1;
            }
            // 最大值和右子节点（i * 2 + 2）进行比较，获取最大值位置
            if ((i * 2 + 2) <= n && arr[maxPos] < arr[i * 2 + 2]) {
                maxPos = i * 2 + 2;
            }
            // 最大值位置是当前元素，结束循环
            if (i == maxPos) {
                break;
            }
            // 交换位置
            swap(arr, i, maxPos);
            // 交换位置继续玩下查找
            i = maxPos;
        }
    }

    /** 交换元素 */
    private static void swap(int[] arr, int i, int k) {
        int tmp = arr[i];
        arr[i] = arr[k];
        arr[k] = tmp;
    }

    /** 测试 */
    public static void main(String[] args) {
        int[] arr = {2, 5, 1, 9, 6, 10, 12, 7};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
