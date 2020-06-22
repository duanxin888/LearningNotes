### Pow(x, n)
实现 pow(x, n) ，即计算 x 的 n 次幂函数。  
示例1：  
>输入: 2.00000, 10  
 输出: 1024.00000  

示例2：  
>输入: 2.10000, 3  
 输出: 9.26100  

示例3：  
>输入: 2.00000, -2  
 输出: 0.25000  

解答：  
```java
/**
采用分治递归法
n^10 --> n^5 --> n^2
时间复杂度：O(log n)
*/
class Solution {
    public double myPow(double x, int n) {
        long N = n;
        return N > 0 ? quickNul(x, N) : 1.0 / quickNul(x, -N);
    }

    private double quickNul(double x, long n) {
        if (n == 0) {
            return 1.0;
        }
        double y = quickNul(x, n / 2);
        return n % 2 == 1 ? y * y * x : y * y;
    }
}
```
