package pers.yf.spring.cloud.ext.auth.route;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public class ForwardCache {

    @CachePut(key = "#name")
    public String cachePut(String name,String url){
        return url;
    }

    @Cacheable()
    public String getCache(String name) {
        return null;
    }
}
