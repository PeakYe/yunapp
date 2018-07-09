package pers.yf.spring.cloud.ext.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.core.IUserManager;
import pers.yf.spring.cloud.ext.auth.server.DefaultUserManger;
import pers.yf.spring.cloud.ext.auth.server.TokenRequestMapping;
import pers.yf.spring.cloud.ext.auth.server.ServerUserCache;

@Configuration
@EnableConfigurationProperties(AuthProperties.class)
@ConditionalOnProperty(prefix = "auth.server", value = "enable", havingValue = "true")
public class AuthServerWebMvcConfigurer extends WebMvcConfigurerAdapter {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
    }


    @Bean
    public TokenRequestMapping loginController() {
        return new TokenRequestMapping();
    }

    @Bean
    public ServerUserCache userCache() {
        return new ServerUserCache();
    }

    @Bean
    public IUserManager userManager() {
        return new DefaultUserManger();
    }


}
