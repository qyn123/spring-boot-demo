package com.qiaoyn.test.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS 中ABA 问题解决
 * @author qiaoyanan
 * date:2023/01/16 14:06
 */
public class ABASolve {

    /**
     * 传递两个值，一个是初始值，一个是初始版本号
     */
    static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100,1);


    public static void main(String[] args) {
        new Thread(()->{
            // 获取版本号
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "第一次获取版本号：" + stamp);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //传入四个值 期望值，更新值，期望版本号，更新版本号
            stampedReference.compareAndSet(100,101,stamp,stamp + 1);
            System.out.println(Thread.currentThread().getName() + "第二次获取版本号：" + stampedReference.getStamp());
            stampedReference.compareAndSet(101,100,stampedReference.getStamp(),stampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "第三次获取版本号：" + stampedReference.getStamp());
        },"t1").start();


        new Thread(()->{
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "第一次获取版本号：" + stamp);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = stampedReference.compareAndSet(100, 2023, stamp, stamp + 1);
            System.out.println(b);
            System.out.println("当前最新版本号：" + stampedReference.getStamp());
            System.out.println("当前最新值：" + stampedReference.getReference());
        },"t2").start();
    }
}
