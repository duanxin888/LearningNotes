package com.duanxin.leetcode;

import java.util.*;

/**
 * @author duanxin
 * @version 1.0
 * @className Solution
 * @date 2020/06/18 09:32
 */
public class Solution {
    public List<List<Integer>> pairSums(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0, len = nums.length; i < len; ++i) {
            int num = target - nums[i];
            if (map.containsKey(num) && map.get(num) > 0 && map.get(nums[i]) > 0) {
                list.add(Arrays.asList(target - nums[i], nums[i]));
                map.put(num, map.get(num) - 1);
            }
            map.put(nums[i], map.get(nums[i]) == null ? 1 : map.get(nums[i]) + 1);
        }
        return list;
    }

    public static void main(String[] args) {
        int[] nums = {5, 6, 5, 6};
        int target = 11;
        System.out.println(new Solution().pairSums(nums, target));
    }
}
