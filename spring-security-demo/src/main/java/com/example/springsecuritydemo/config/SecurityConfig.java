package com.example.springsecuritydemo.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author qiaoyanan
 * date:2022/10/07 20:22
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //请求授权的规则
        http.authorizeHttpRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/level1/**").hasRole("vip1")
        .antMatchers("/level2/**").hasRole("vip2")
        .antMatchers("/level3/**").hasRole("vip3");

        //没有权限会跳转到登录页,需要开启登录的页面,指定登录页
        http.formLogin().loginPage("/toLogin");
        // 关闭防范csrf攻击
        http.csrf().disable();
        //登出
        http.logout();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder)
                .withUser("tom").password(bCryptPasswordEncoder.encode("123456")).roles("vip1","vip2")
                .and()
                .withUser("admin").password(bCryptPasswordEncoder.encode("123456")).roles("vip1", "vip2","vip3")
                .and()
                .withUser("guest").password(bCryptPasswordEncoder.encode("123456")).roles("vip1");
    }
}
