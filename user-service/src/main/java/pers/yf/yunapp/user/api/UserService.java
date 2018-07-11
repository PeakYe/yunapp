package pers.yf.yunapp.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yf.spring.cloud.ext.auth.UserDetail;
import pers.yf.yunapp.user.service.UserDao;

@RestController()
@RequestMapping("")
public class UserService {

    @Autowired
    protected UserDao userDao;

    @RequestMapping("/info")
    public String user(UserDetail userDetail) {
        if (userDetail != null) {
            return userDetail.getUserName();
        }
        return "hello";
    }
}
