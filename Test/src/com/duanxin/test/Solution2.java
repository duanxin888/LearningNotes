package com.duanxin.test;

import java.util.*;

/**
 * 公司进购一批货物，假设进购预算为 k，这批货物每种货物有价格P，价值V，每种货物不限购买件数，求在预算之下求出最高价值数
 *
 * 输入：
 *      第一行：预算（k < 200000）
 *      第二行：货物种数（n < 20）
 *      接下来每行输入每种货物的 价格 价值
 * 输出：购买总价值
 *
 * 例子：
 *      100
 *      5
 *      22 22
 *      77 90
 *      23 21
 *      50 89
 *      12 12
 *      结果：112
 *
 * @author duanxin
 * @version 1.0
 * @className Solution2
 * @date 2020/08/01 16:04
 */
public class Solution2 {

    private static int valueCount = 0;

    private static int budget = 0;

    private static int minMoney = Integer.MAX_VALUE;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        budget = Integer.parseInt(sc.nextLine());
        if (budget > 200000) {
            return;
        }
        int count = Integer.parseInt(sc.nextLine());
        if (count > 20) {
            return;
        }

        int[] supplies = new int[count];
        int[] values = new int[count];
        for (int i = 0; i < count; ++i) {
            String[] s = sc.nextLine().split(" ");
            supplies[i] = Integer.parseInt(s[0]);
            values[i] = Integer.parseInt(s[1]);
            if (minMoney > supplies[i]) {
                minMoney = supplies[i];
            }
        }

        Map<Integer, Integer> valueMap = new HashMap<>();
        for (int i = 0, len = values.length; i < len; ++i) {
            valueMap.put(i, values[i]);
        }
        List<Map.Entry<Integer, Integer>> valueList = new ArrayList<>(valueMap.entrySet());
        valueList.sort((o1, o2) -> -o1.getValue().compareTo(o2.getValue()));

        // 计算
        calculation(supplies, valueList);
        System.out.println(valueCount);
    }

    private static void calculation(int[] supplies, List<Map.Entry<Integer, Integer>> valueList) {
        if (budget < 0) {
            return ;
        }

        for (int i = 0, size = valueList.size(); i < size; ++i) {
            if (budget < minMoney) {
                break;
            }
            int money = supplies[valueList.get(i).getKey()];
            int value = valueList.get(i).getValue();
            if (budget > money) {
                budget -= money;
                valueCount += value;
                calculation(supplies, valueList);
            }
        }
    }

}
