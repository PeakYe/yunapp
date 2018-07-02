package pers.yf.spring.cloud.ext.auth.route;

import org.springframework.cache.annotation.Cacheable;
import pers.yf.spring.cloud.ext.auth.UserDetail;

public interface IUserCacheService {
    UserDetail getUserDetail(String token);

    String getUserDetialJson(String token);
}
