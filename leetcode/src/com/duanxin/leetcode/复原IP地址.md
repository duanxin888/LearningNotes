### 复原IP地址  
给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。  
有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 '.' 分隔。  
示例：  
>输入: "25525511135"  
 输出: ["255.255.11.135", "255.255.111.35"]  

解法一：  
```java
class Solution {
    public List<String> restoreIpAddresses(String s) {
        // 迭代法
        List<String> res = new ArrayList<>();
        int len = s.length();
        if (len < 4 || len > 12) {
            return res;
        }
        for (int i = 1; i < 4 && i < len - 2; ++i) {
            for (int j = i + 1; j < i + 4 && j < len - 1; ++j) {
                for (int k = j + 1; k < j + 4 && k < len; ++k) {
                    String s1 = s.substring(0, i);
                    String s2 = s.substring(i, j);
                    String s3 = s.substring(j, k);
                    String s4 = s.substring(k, len);
                    if (isValid(s1) && isValid(s2) && isValid(s3) && isValid(s4)) {
                        res.add(s1 + "." + s2 + "." + s3 + "." + s4);
                    }
                }
            }
        }
        return res;
    }

    private boolean isValid(String s) {
        if (s.length() > 3 || s.length() == 0 || (s.charAt(0) == '0' && s.length() > 1) || Integer.parseInt(s) > 255) {
            return false;
        }
        return true;
    }
}
```  
解法二：  
```java
/*
dfs关键在于怎么找到 “terminate --> recursion --> rollback” 三个点，其中 “terminate --> recursion”是必须的
*/
class Solution {
    public List<String> restoreIpAddresses(String s) {
        // 递归法
        List<String> res = new ArrayList<>();
        int len = s.length();
        if (len < 4 || len > 12) {
            return res;
        }
        dfs(res, s, 0, "", 0);
        return res;
    }

    private void dfs(List<String> res, String s, int indx, String result, int count) {
        // terminate
        if (count > 4) {
            return;
        }
        if (count == 4 && indx == s.length()) {
            res.add(result);
        }

        // recursion
        for (int i = 1; i < 4; ++i) {
            if (indx + i > s.length()) {
                break;
            }
            String str = s.substring(indx, indx + i);
            if ((str.charAt(0) == '0' && str.length() > 1) || Integer.parseInt(str) > 255) {
                continue;
            }
            dfs(res, s, indx + i, result + str + (count == 3 ? "" : "."), count + 1);
        }
    }
}
```
