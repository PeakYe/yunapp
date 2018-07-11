package pers.yf.spring.cloud.ext.auth.core;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yf.spring.cloud.ext.auth.server.ServerUserCache;

@Service
public class DefaultUserCacheManager implements IUserCacheService {

    @Autowired
    protected ServerUserCache serverUserCache;


    @Override
    public String getUserDetialJson(String token) {
        return new JSONObject(serverUserCache.getCacheUser(token)).toString();
    }


}
