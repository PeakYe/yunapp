package pers.yf.yunapp.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.config.CacheManagementConfigUtils;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class UserCacheService {


    @Cacheable(value = "user")
    public String getUserDetialJson(String token) {
        //for (String s : manager.getCacheNames()) {
        //    System.out.println(s);
        //}
        return "valie - " + System.currentTimeMillis();
    }

}
