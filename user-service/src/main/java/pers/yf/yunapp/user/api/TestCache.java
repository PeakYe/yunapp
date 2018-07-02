package pers.yf.yunapp.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestCache {

    @Autowired
    UserCacheService cacheService;

    @RequestMapping("cache")
    @ResponseBody
    public String getCache(){
        return cacheService.getUserDetialJson("nihao");

    }
}
