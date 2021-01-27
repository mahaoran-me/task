package com.task.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.dto.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/login")
                .successHandler((httpServletRequest, httpServletResponse, authentication)
                        -> responseProcess(httpServletResponse, new SimpleResponse(1, "登录成功")))
                .failureHandler((httpServletRequest, httpServletResponse, e)
                        -> responseProcess(httpServletResponse, new SimpleResponse(0, "登录失败")))
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication)
                        -> responseProcess(httpServletResponse, new SimpleResponse(1, "登出成功")))
                .and()
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e)
                        -> responseProcess(httpServletResponse, new SimpleResponse(-1, "请先登录")));
    }

    private void responseProcess(HttpServletResponse httpResponse, SimpleResponse simpleResponse) throws IOException {
        httpResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpResponse.getWriter();
        out.write(new ObjectMapper().writeValueAsString(simpleResponse));
        out.flush();
        out.close();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        return s.equals(encode(charSequence));
                    }
                });
    }
}
