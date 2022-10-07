package com.example.demo.controller;

/**
 * @author qiaoyanan
 * date:2022/09/26 14:43
 */
public class Test {

    public static void main(String[] args) {
        test(1);
    }
    public static void test(int a) {
        try {
            if (a == 1){
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println(11111);
            e.printStackTrace();
        }
    }
}
