package com.qiaoyn.direct;

/**
 * @author qiaoyanan
 * date:2022/10/19 15:34
 */
public class RoundRobinConsumerThread {
    public static void main(String[] args) {
        new Thread(new RoundRobinConsumerA(),"RoundRobinConsumerA").start();
        new Thread(new RoundRobinConsumerB(),"RoundRobinConsumerB").start();
    }
}
