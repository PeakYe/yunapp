package pers.yf.spring.cloud.ext.auth.route;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.UserDetail;


public class UserCacheService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthProperties authConfiguration;

    @Cacheable(cacheNames = "user")
    public UserDetail getUserDetial(String token) {
        return restTemplate.getForObject("http://" + authConfiguration.getAuthService() + "/token", UserDetail.class);
    }

    @Cacheable(cacheNames = "user")
    public String getUserDetialJson(String token) {
        UserDetail detail = restTemplate.getForObject("http://" + authConfiguration.getAuthService() + "/token", UserDetail.class);
        if (detail != null) {
            return new JSONObject(detail).toString();
        }
        return null;
    }
}
