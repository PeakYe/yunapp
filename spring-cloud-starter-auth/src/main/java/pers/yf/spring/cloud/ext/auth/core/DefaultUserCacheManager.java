package pers.yf.spring.cloud.ext.auth.core;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pers.yf.spring.cloud.ext.auth.server.ServerUserCache;

@Service
public class DefaultUserCacheManager extends ServerUserCache implements IUserCacheService {
//    @Override
//    @Cacheable(value = "user", key = "#token")
//    public UserDetail getUserDetail(String token) {
//        return null;
//    }


    @Override
    public String getUserDetialJson(String token) {
        return new JSONObject(super.getCacheUser(token)).toString();
    }


}
