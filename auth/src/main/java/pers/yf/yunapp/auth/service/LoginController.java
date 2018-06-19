package pers.yf.yunapp.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.yf.yunapp.auth.service.model.CacheServiceTest;
import pers.yf.yunapp.auth.service.model.UserDetail;

@Controller
@RequestMapping("auth")
public class LoginController {

    @Autowired
    CacheServiceTest serviceTest;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return "auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(String redirect) {
        UserDetail user = new UserDetail();
        return "redirect:" + redirect;
    }

    @RequestMapping(value = "/loginx", method = RequestMethod.GET)
    @ResponseBody
    public String loginX() {
        String demo = "hello";
//        setCache(demo);
        return serviceTest.readCache(demo);
    }

    @CachePut(key = "#demo")
    public String setCache(String demo) {
        return "123456";
    }


}
