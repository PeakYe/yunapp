package pers.yf.spring.cloud.ext.auth.route;

import org.springframework.cache.annotation.Cacheable;
import pers.yf.spring.cloud.ext.auth.UserDetail;

public interface IUserCacheService {
    @Cacheable(cacheNames = "user")
    UserDetail getUserDetial(String token);

    @Cacheable(cacheNames = "user")
    String getUserDetialJson(String token);
}
