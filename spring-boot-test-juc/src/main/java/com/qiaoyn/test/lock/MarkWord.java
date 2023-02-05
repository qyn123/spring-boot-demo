package com.qiaoyn.test.lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author qiaoyanan
 * date:2023/02/02 10:22
 */
public class MarkWord {

    public final Object object = new Object();

    public synchronized void demo() {

        synchronized (object) {
            System.out.println("synchronized代码块内");
            System.out.println(ClassLayout.parseInstance(object).toPrintable());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("等待4s前");
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
        TimeUnit.SECONDS.sleep(4);

        MarkWord markWord = new MarkWord();
        System.out.println("等待4s后");
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
        Thread thread;
        thread = new Thread(markWord::demo);
        thread.start();
        thread.join();
        System.out.println(ClassLayout.parseInstance(markWord.object).toPrintable());

    }
}
