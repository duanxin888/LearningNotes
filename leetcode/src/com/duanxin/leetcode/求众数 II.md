### 求众数 II  
给定一个大小为 n 的数组，找出其中所有出现超过 ⌊ n/3 ⌋ 次的元素。  
说明: 要求算法的时间复杂度为 O(n)，空间复杂度为 O(1)。  
**示例 1:**  
>输入: [3,2,3]  
 输出: [3]  

**示例 2:**  
>输入: [1,1,1,3,3,2,2,2]  
 输出: [1,2]  

解答：  
```java
/**
摩尔投票法：该方法分为两个阶段，投票与计数两个阶段，解决的解决的问题是如何在任意多的候选人中，
选出票数超过一半的那个人。注意，是超出一半票数的那个人。假设投票是这样的，[A, C, A, A, B]，
ABC是指三个候选人。第一张票与第二张票进行对坑，如果票不同则互相抵消掉；
接着第三票与第四票进行对坑，如果票相同，则增加这个候选人的可抵消票数；
这个候选人拿着可抵消票数与第五张票对坑，如果票不同，则互相抵消掉，即候选人的可抵消票数-1。

该方法得出一条总结规律：
        如果至多选一个代表，那么那他的票数至少要超过一半（⌊ 1/2 ⌋）的票数；
        如果至多选两个代表，那他们的票数至少要超过⌊ 1/3 ⌋的票数；
        如果至多选m个代表，那他们的票数至少要超过⌊ 1/(m+1) ⌋的票数。
*/
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }

        int num1 = nums[0], count1 = 0;
        int num2 = nums[0], count2 = 0;

        // 摩尔投票法分两个阶段：投票阶段和计数阶段
        for (int num : nums) {
            // 投票阶段
            if (num == num1) {
                count1++;
                continue;
            }
            if (num == num2) {
                count2++;
                continue;
            }
            if (count1 == 0) {
                num1 = num;
                count1++;
                continue;
            }
            if (count2 == 0) {
                num2 = num;
                count2++;
                continue;
            }
            count1--;
            count2--;
        }

        // 计数阶段，计算选举人的票数是否超过 三分之一
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == num1) {
                count1++;
                continue;
            }
            if (num == num2) {
                count2++;
            }
        }
        if (count1 > nums.length / 3) {
            res.add(num1);
        }
        if (count2 > nums.length / 3) {
            res.add(num2);
        }
        return res;
    }
}
```