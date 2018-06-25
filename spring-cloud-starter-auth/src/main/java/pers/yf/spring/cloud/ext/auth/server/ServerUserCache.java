package pers.yf.spring.cloud.ext.auth.server;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.yf.spring.cloud.ext.auth.UserDetail;

@Service
public class ServerUserCache {

    @Cacheable(cacheNames = "user", key = "#token")
    public UserDetail cacheUser(String token, UserDetail detail) {
        return detail;
    }

    @Cacheable(cacheNames = "user", key = "#token")
    public UserDetail getCacheUser(String token) {
        return null;
    }

}
