package com.qiaoyn.test.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiaoyanan
 * date:2023/01/16 15:38
 */
public class A {

   //int num = 0;
    AtomicInteger num = new AtomicInteger(0);

    public long getNum() {
        return num.get();
        //return num;
    }


//    public  void increase () {
//        synchronized (this) {
//            num ++;
//        }
//    }

    public  void increase () {
        num.getAndIncrement();
    }


}
