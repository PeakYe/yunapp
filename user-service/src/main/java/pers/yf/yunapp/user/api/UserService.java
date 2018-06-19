package pers.yf.yunapp.user.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserService {

    @RequestMapping("/info")
    public String user() {
        return "hello";
    }
}
