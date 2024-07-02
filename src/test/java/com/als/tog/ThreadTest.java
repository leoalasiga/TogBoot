package com.als.tog;

public class ThreadTest {

    // 共享变量，用于演示线程同步
    private static int counter = 0;
    private static final Object lock = new Object();

    // 自定义线程类
    static class CustomThread extends Thread {
        @Override
        public void run() {
            // 在循环中修改共享变量
            for (int i = 0; i < 1000; i++) {
                incrementCounter();
            }
            System.out.println(Thread.currentThread().getName() + " finished.");
        }

        // 使用synchronized方法来保证线程安全
        private synchronized void incrementCounter() {
            counter++;
        }
    }

    public static void main(String[] args) {
        // 创建两个线程
        CustomThread thread1 = new CustomThread();
        CustomThread thread2 = new CustomThread();

        // 启动线程
        thread1.start();
        thread2.start();

        // 等待两个线程执行完毕
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 输出最终的counter值
        System.out.println("Final counter value: " + counter);
    }
}
