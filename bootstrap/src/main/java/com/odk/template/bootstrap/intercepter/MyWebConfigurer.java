package com.odk.template.bootstrap.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MyWebConfigurer
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/12/20
 */
@Configuration
public class MyWebConfigurer implements WebMvcConfigurer {

    @Override
    /*添加跨域资源共享请求*/
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域，使用这种配置方法就不能在 interceptor 中再配置 header 了
        /*需要进行的配置包括
         * 请求路径：/**所有请求都可以跨域
         * allowCredentials:是否允许请求cookie
         * allowedOrigins:允许访问的源：由协议+域名+端口号组成，表示请求的源
         * allowedMethods：允许访问的请求方式
         * allowedHeaders：允许访问的请求头
         * */
        registry.addMapping("/**")
                .allowCredentials(true)
//                .allowedOrigins("http://localhost:80")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
