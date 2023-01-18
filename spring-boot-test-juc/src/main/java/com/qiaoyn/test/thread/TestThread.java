package com.qiaoyn.test.thread;

/**
 * 无状态锁->偏向锁->轻量级锁->重量级锁
 * @author qiaoyanan
 * date:2023/01/16 15:30
 */
public class TestThread {

    public static final Integer times = 10000000;

    public static void main(String[] args) {
        A a = new A();
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < times; i++) {
                a.increase();
            }
        });
        t1.start();

        for (int i = 0; i < times; i++) {
            a.increase();
        }
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("共耗时"+(end - start) + "ms");
        System.out.println(a.getNum());
    }
}
