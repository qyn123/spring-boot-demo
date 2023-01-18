package com.qiaoyn.test.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS
 * 1)存在的ABA问题
 * 2) 原子性问题 ： lock + cmpxchgq
 * @author qiaoyanan
 * date:2023/01/16 10:04
 */
public class ABAProblem {

    /**
     * 普通的原子类引用包装问题
     */
   static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    public static void main(String[] args) {

        new Thread(() -> {
            // 把100改成101，在把101改成100
            atomicReference.compareAndSet(100,101);
        },"t1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 把100改成101，在把101改成100
            atomicReference.compareAndSet(101,100);
        },"t3").start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 把100改成2023
            System.out.println(atomicReference.compareAndSet(100, 2023) +"\t"+ atomicReference.get() );
        },"t2").start();
    }
}
