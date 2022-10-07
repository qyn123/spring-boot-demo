package com.example.demo.test;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiaoyanan
 * date:2022/07/12 15:57
 */
public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder b1 = new BCryptPasswordEncoder();
        BCryptPasswordEncoder b2 = new BCryptPasswordEncoder(4);
        Pbkdf2PasswordEncoder b3 = new Pbkdf2PasswordEncoder();
        SCryptPasswordEncoder b4 = new SCryptPasswordEncoder();
        Argon2PasswordEncoder b5 = new Argon2PasswordEncoder();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            System.out.println(b5.encode("aaaabbbbccccddddeeeeffff"));
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
