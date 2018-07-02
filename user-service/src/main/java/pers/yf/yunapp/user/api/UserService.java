package pers.yf.yunapp.user.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yf.spring.cloud.ext.auth.UserDetail;

@RestController()
@RequestMapping("")
public class UserService {

    @RequestMapping("/info")
    public String user(UserDetail userDetail) {
        if (userDetail != null) {
            return userDetail.getUserName();
        }
        return "hello";
    }
}
