package com.duanxin.thread;

/**
 * @author duanxin
 * @version 1.0
 * @className SyncDemo4
 * @date 2020/07/05 14:54
 */
public class SyncDemo4 {
    public static void main(String[] args) {
        synchronized (SyncDemo4.class) {
            System.out.println("test synchronized class");
        }
    }

    synchronized void sync() {
        System.out.println("test synchronized class");
    }
}
