package com.duanxin.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author duanxin
 * @version 1.0
 * @className Solution
 * @date 2020/06/18 09:32
 */
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();
        sc.close();
        String[] moneys = s1.split(" ");
        Map<Integer, Integer> moneyMap = new HashMap<>();
        Integer[] money = {1, 5, 10, 50, 100};
        int index = 0;
        for (String m : moneys) {
            moneyMap.put(money[index++], Integer.parseInt(m));
        }
        int k = Integer.parseInt(s2);

        int num = 0;
        for (;;) {
            if (k < 1) {
                break;
            }
            if (k >= 100 && moneyMap.get(100) > 0) {
                num++;
                k -= 100;
                moneyMap.putIfAbsent(100, moneyMap.get(100) - 1);
            } else if (k >= 50 && moneyMap.get(50) > 0) {
                num++;
                k -= 50;
                moneyMap.putIfAbsent(50, moneyMap.get(50) - 1);
            } else if (k >= 10 && moneyMap.get(10) > 0) {
                num++;
                k -= 10;
                moneyMap.putIfAbsent(10, moneyMap.get(10) - 1);
            } else if (k >= 5 && moneyMap.get(5) > 0) {
                num++;
                k -= 5;
                moneyMap.putIfAbsent(5, moneyMap.get(5) - 1);
            } else if (k >= 1 && moneyMap.get(1) > 0) {
                num++;
                k -= 1;
                moneyMap.putIfAbsent(1, moneyMap.get(1) - 1);
            }
        }
        if (num == 0) {
            System.out.println(-1);
        } else {
            System.out.println(num);
        }
    }
}
