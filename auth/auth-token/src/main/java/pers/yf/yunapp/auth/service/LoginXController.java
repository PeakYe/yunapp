package pers.yf.yunapp.auth.service;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/auth")
public class LoginXController {
//
//    @Autowired
//    UserManager manager;
//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String loginGet() {
//        return "page/login";
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String loginPost(String username, String password, String redirect, Model model) {
//        UserDetail user = new UserDetail();
//        user.setUserName(username);
//        String token = UUID.randomUUID().toString();
//        manager.cacheUser(token, user);
//        if (redirect == null) {
//            model.addAttribute("token", token);
//            return "pers/yf/yunapp/auth/tokeninfo";
//        }
//        return "redirect:" + redirect.replaceAll("\\[token\\]", token);
//    }
//
//    @RequestMapping(value = "/token", method = RequestMethod.POST)
//    @ResponseBody
//    public UserDetail token(String token) {
//        if (token == null) {
//            return null;
//        }
//        UserDetail user = manager.getCacheUser(token);
//        return user;
//    }

}
