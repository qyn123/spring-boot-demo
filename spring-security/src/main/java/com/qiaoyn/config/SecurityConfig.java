package com.qiaoyn.config;

import com.qiaoyn.filter.JWTAuthenticationFilter;
import com.qiaoyn.filter.JWTAuthorizationFilter;
import com.qiaoyn.service.impl.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author qiaoyanan
 * date:2022/09/29 15:17
 */
@EnableWebSecurity
// 只有加了@EnableGlobalMethodSecurity(prePostEnabled=true) 那么在上面使用的 @PreAuthorize(“hasAuthority(‘admin’)”)才会生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUserService jwtUserService;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 这边 通过重写configure(),去数据库查询用户是否存在
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                // 以/user 开头的请求 都需要进行验证
                .antMatchers("/user/**")
                .authenticated()
                // 其他都放行了
                .anyRequest().permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager())) // 用户登录拦截
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))  // 权限拦截
                // 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}

