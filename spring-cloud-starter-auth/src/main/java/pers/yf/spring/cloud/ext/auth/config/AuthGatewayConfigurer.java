package pers.yf.spring.cloud.ext.auth.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.route.AutherFilter;
import pers.yf.spring.cloud.ext.auth.route.ForwardCache;
import pers.yf.spring.cloud.ext.auth.route.IUserCacheService;
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
    @ConditionalOnMissingBean
    public IUserCacheService userCacheService() {
        return new UserCacheService();
    }

    @Bean
    @ConditionalOnMissingBean()
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    @ConditionalOnMissingClass
    public ForwardCache forwardCache() {
        return new ForwardCache();
    }
}
