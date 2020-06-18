package com.duanxin.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duanxin
 * @version 1.0
 * @className Solution
 * @date 2020/06/18 09:32
 */
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        int max = 2047;
        int[] res = new int[max + 1];
        for (int i = 0, len = nums.length; i < len; ++i) {
            int diff = max & (target - nums[i]);
            if (res[diff] != 0) {
                return new int[] {res[diff] -1 , i};
            }
            res[max & nums[i]] = i + 1;
        }
        return new int[] {};
    }

    public static void main(String[] args) {
        int[] nums = {3025, 5, 4025, 15};
        int target = 1;
        System.out.println(Arrays.toString(new Solution().twoSum(nums, target)));
    }
}
