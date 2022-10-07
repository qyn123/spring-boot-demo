package com.qiaoyn.upload.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author qiaoyanan
 * date:2022/08/23 15:27
 */
public class Test1 {
    public static void main(String[] args) {
        List<Integer> aList = new ArrayList<>();
        List<Integer> bList = new ArrayList<>();
        Arrays.asList(1, 2, 3, 4).forEach(a -> {

            if (a % 2 == 0) {
                try {
                    Integer i = 1 / 0;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    ;
                }
                aList.add(a);
            } else {
                bList.add(a);
            }
        });
        System.out.println(aList);
        System.out.println(bList);

    }
}
