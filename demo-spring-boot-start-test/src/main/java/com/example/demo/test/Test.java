package com.example.demo.test;

/**
 * @author qiaoyanan
 * date:2022/07/04 10:38
 */
public class Test {

        public static void main(String args[]) {
            String Str = new String("    www .run oob. com    ");
            System.out.print("原始值 :" );
            System.out.println( Str );

            System.out.print("删除头尾空白 :" );
            System.out.println( Str.trim() );
        }
}
