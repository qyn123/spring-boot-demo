package com.qiaoyn.direct;

/**
 * @author qiaoyanan
 * date:2022/10/19 15:34
 */
public class FairConsumerThread {
    public static void main(String[] args) {
        new Thread(new FairConsumerA(),"FairConsumerA").start();
        new Thread(new FairConsumerB(),"FairConsumerB").start();
    }
}
