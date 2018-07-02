package pers.yf.spring.cloud.ext.auth.route;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.HttpUtil;
import pers.yf.spring.cloud.ext.auth.UserDetail;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserCacheService implements IUserCacheService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthProperties authConfiguration;

    @Override
    @CachePut(cacheNames = "user")
    public UserDetail getUserDetail(String token) {
        return restTemplate.getForObject(authConfiguration.getAuthService() + authConfiguration.getLoginUrl() + "/token", UserDetail.class);
    }

    @Override
    @Cacheable(value = "user",key = "#token")
    public String getUserDetialJson(String token) {
        //UserDetail detail = new UserDetail();
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        String result = HttpUtil.post(authConfiguration.getAuthService() + authConfiguration.getTokenUrl(), map);
        //if (!StringUtils.isEmpty(result)) {
        //    JSONObject json=new JSONObject(result);
        //    detail.setId(json.optString("id"));
        //    detail.setUserName(json.optString("userName"));
        //    return detail;
        //}

        return result;
    }

}
