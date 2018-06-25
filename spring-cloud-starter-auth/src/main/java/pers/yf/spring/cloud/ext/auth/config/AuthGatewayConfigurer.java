package pers.yf.spring.cloud.ext.auth.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.route.AutherFilter;
import pers.yf.spring.cloud.ext.auth.route.UserCacheService;

@Configuration
@ConditionalOnProperty(prefix = "auth.route", value = "enable", havingValue = "true")
public class AuthGatewayConfigurer {


    @Bean
    public AutherFilter autherFilter() {
        return new AutherFilter();
    }


    @Bean
    public AuthProperties authProperties(){
        return new AuthProperties();
    }

    @Bean
    public UserCacheService userCacheService() {
        return new UserCacheService();
    }

}
