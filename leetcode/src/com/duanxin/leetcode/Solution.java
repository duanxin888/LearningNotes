package com.duanxin.leetcode;

import java.util.*;

/**
 * @author duanxin
 * @version 1.0
 * @className Solution
 * @date 2020/06/18 09:32
 */
public class Solution {
    public static List<List<Integer>> subsets(int[] nums) {
        // 迭代法
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for (int num : nums) {
            List<Integer> cur = new ArrayList<>();
            cur.add(num);
            for (int j = 0, size = res.size(); j < size; ++j) {
                cur.addAll(res.get(j));
                res.add(cur);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(subsets(nums));
    }
}
