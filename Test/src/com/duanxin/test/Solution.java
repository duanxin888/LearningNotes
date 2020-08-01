package com.duanxin.test;

/**
 * 输入一个 int数组，进行排列组合，可进行重复，求出这些组合数是 7 的倍数的种数。
 *
 * 例子：
 *      [1,1,2] （112，112，121，121，211，211）
 *      结果：2
 *
 * @author duanxin
 * @version 1.0
 * @className Solution
 * @date 2020/08/01 15:41
 */
public class Solution {

    private boolean[] used;

    private int count = 0;

    /**
     * 返回亲7数个数
     * @param digit int整型一维数组 组成亲7数的数字数组
     * @return int整型
     */
    public int reletive_7 (int[] digit) {
        // write code here
        if (digit.length == 0) {
            return count;
        }

        used = new boolean[digit.length];
        // 排列组合
        int sum = 0;
        generatePermutation(digit, 0, sum);
        return count;
    }

    private void generatePermutation(int[] digit, int index, int sum) {
        if (index == digit.length) {
            if (sum % 7 == 0) {
                count++;
            }
            return ;
        }

        for (int i = 0, len = digit.length; i < len; ++i) {
            if (!used[i]) {
                sum += digit[i] * Math.pow(10, (len - index - 1));
                used[i] = true;
                generatePermutation(digit, index + 1, sum);
                // 回溯
                sum -= Math.pow(10, len - index - 1) * digit[i];
                used[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        int[] digit = {1, 1, 2};
        System.out.println(new Solution().reletive_7(digit));
    }
}
