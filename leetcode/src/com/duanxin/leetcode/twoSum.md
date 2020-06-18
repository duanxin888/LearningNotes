### 两数之和  
示例：  
>给定 nums = [2, 7, 11, 15], target = 9  
>因为 nums[0] + nums[1] = 2 + 7 = 9  
>所以返回 [0, 1]  

解答：  
```java
// 解法一：暴力解法  时间复杂度 O(n^2)
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int len = nums.length; 
        for (int i = 0; i < len -1 ; ++i) {
            for (int j = i + 1; j < len; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {};
    }
}
```
```java
// 解法二：Hash法  时间复杂度 O(n)  空间复杂度 O(n)
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0, len = nums.length; i < len; ++i) {
            if (map.containsKey(target - nums[i])) {
                return new int[] {i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return new int[] {};
    }
}
```
```java
// 解法二：位运算比较法  时间复杂度 O(n)  空间复杂度 O(1)
class Solution {
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
}
```