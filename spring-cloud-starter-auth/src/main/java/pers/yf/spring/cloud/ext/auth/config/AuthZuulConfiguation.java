package pers.yf.spring.cloud.ext.auth.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.core.DefaultUserCacheManager;
import pers.yf.spring.cloud.ext.auth.core.IUserCacheService;
import pers.yf.spring.cloud.ext.auth.core.IUserManager;
import pers.yf.spring.cloud.ext.auth.core.LoginRequestHandler;
import pers.yf.spring.cloud.ext.auth.route.AutherFilter;
import pers.yf.spring.cloud.ext.auth.route.AutherLoginFilter;
import pers.yf.spring.cloud.ext.auth.server.DefaultUserManger;
import pers.yf.spring.cloud.ext.auth.server.ServerUserCache;

@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthZuulConfiguation {

    @Bean
    @ConditionalOnMissingBean
    public ServerUserCache serverUserCache() {
        return new ServerUserCache();
    }

    @Bean
    @ConditionalOnMissingBean
    public IUserManager userManager() {
        return new DefaultUserManger();
    }


    @Bean
    public LoginRequestHandler loginRequestController(){
        return new LoginRequestHandler();
    }

    @Bean
    public AutherFilter autherFilter() {
        return new AutherFilter();
    }

    @Bean
    public AutherLoginFilter autherLoginFilter() {
        return new AutherLoginFilter();
    }


    @Bean
    @ConditionalOnMissingBean
    public IUserCacheService userCacheService(){
        return new DefaultUserCacheManager();
    }
}
