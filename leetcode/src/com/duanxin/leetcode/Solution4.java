package com.duanxin.leetcode;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author duanxin
 * @version 1.0
 * @className Solution
 * @date 2020/06/18 09:32
 */
public class Solution4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        sc.close();
        if (str.length() < 1) {
            return ;
        }
        char[] chs = new char[str.length()];

        int j = 0;
        for (int i = 0, len = str.length(); i < len; ++i) {
            char c = str.charAt(i);
            if (c == '-' && chs[0] == '\u0000') {
                chs[j++] = c;
                continue;
            }
            if (isDigital(c)) {
                // remove the first zero character
                if ((c == '0' || c == '+') && chs[0] == '\u0000'){
                    continue;
                }
                chs[j++] = c;
            }
        }

        System.out.println(new String(chs));
    }

    private static boolean isDigital(char charAt) {
        return charAt >= '0' && charAt <= '9';
    }
}
