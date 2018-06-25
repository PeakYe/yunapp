package pers.yf.yunapp.auth.service.model;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceTest {
    @Cacheable(cacheNames = "demo")
    public String readCache(String demo) {
        return "123445";
    }
}
