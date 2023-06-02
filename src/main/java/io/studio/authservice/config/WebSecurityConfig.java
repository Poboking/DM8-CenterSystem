package io.studio.authservice.config;

import io.studio.authservice.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/8 11:21
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig implements WebSecurityConfigurer<WebSecurity> {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private UserDetailsService userDetailsService;


    private JwtTokenFilter jwtTokenFilter = new JwtTokenFilter();

    @Override
    public void init(WebSecurity builder) throws Exception {
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/resources/**","/user/register","/user/login","/api/validate");
        // 对于请求 "/resources/**" 不做安全控制
        // 并 允许 "/user/register" 和 "/user/login" 两个 URL 不需要认证即可访问
    }

    /**
     * 配置 HttpSecurity，用于定义认证和授权规则
     */
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用 CSRF
                .authorizeRequests()
//                .requestMatchers("/user/register", "/user/login").permitAll()
//                // 允许 "/user/register" 和 "/user/login" 两个 URL 不需要认证即可访问
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 对于 HTTP 方法为 OPTIONS 的任意请求不做安全控制
                .anyRequest().authenticated()
                // 其他请求需要认证通过才能访问
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                // 定义会话管理策略为无状态会话

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加 JWT Token 过滤器
    }

    /**
     * 配置 AuthenticationManagerBuilder，用于定义用户信息获取渠道和密码加密方式
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        // 指定用户信息获取渠道为 userDetailsService，并指定密码加密方式为 passwordEncoder
    }

    /**
     * 定义 AuthenticationManager Bean，用于处理认证请求
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        // 获取 AuthenticationManager 对象
        return authenticationManager;
    }

    /**
     * 定义 PasswordEncoder Bean，用于对密码进行加密和解密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 使用 BCryptPasswordEncoder 作为密码加密方式
    }
}