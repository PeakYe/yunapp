package pers.yf.yunapp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import pers.yf.spring.cloud.ext.auth.core.LoginRequestHandler;
import pers.yf.spring.cloud.ext.auth.core.annon.EnableAuthCenter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableCaching
@EnableAuthCenter
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }


    @Bean
    public LoginRequestHandler loginRequestController(){
        return new LoginRequestHandler();
    }
}
