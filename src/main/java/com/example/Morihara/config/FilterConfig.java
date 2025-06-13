package com.example.Morihara.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new LoginFilter());
        //ログイン情報が必要なURL
        //bean.addUrlPatterns("/home");
        bean.addUrlPatterns("/userEdit/*");
        bean.addUrlPatterns("/singUp");
        bean.setOrder(1);
        return bean;
    }
}
