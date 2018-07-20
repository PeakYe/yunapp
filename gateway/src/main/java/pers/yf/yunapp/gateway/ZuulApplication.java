package pers.yf.yunapp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import pers.yf.spring.cloud.ext.auth.config.AuthZuulConfiguation;
import pers.yf.spring.cloud.ext.auth.core.annon.EnableAuthCenter;
import pers.yf.yunapp.gateway.config.StaticResourcesConfig;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableCaching
@EnableAuthCenter
@Import(AuthZuulConfiguation.class)
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public StaticResourcesConfig staticResourcesConfig() {
        return new StaticResourcesConfig();
    }

}
