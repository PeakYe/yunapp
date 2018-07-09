package pers.yf.spring.cloud.ext.auth.server;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.UserDetail;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class TokenRequestMapping {

    @Autowired
    ServerUserCache cacheUser;

    @Autowired
    IUserManager userManager;

    @Autowired
    AuthProperties properties;

    //@RequestMapping(value = "/login", method = RequestMethod.GET)
    //public String loginGet() {
    //    return "auth/login";
    //}

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@RequestBody LoginReq req, Model model, HttpServletRequest request, HttpServletResponse response) {
        UserDetail user = userManager.getUserByName(req.getUsername());
        boolean validate = false;
        String token = null;
        if (user == null) {
            validate = false;
        } else {
            if (userManager.validatePassword(user, req.getPassword())) {
                token = UUID.randomUUID().toString();
                cacheUser.cacheUser(token, user);
                response.setHeader(properties.getForwardHeader(), token);
                response.addCookie(new Cookie("token", token));
                validate = true;
            }

        }

        if (validate) {
            if (req.getRedirect() == null) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("code", "ok");
                    json.put("data", token);
                    response.getWriter().write(json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            return "redirect:" + req.getRedirect().replaceAll("\\[token\\]", token);

        } else {
            if (req.getRedirect() == null) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("code", "valid_err");
                    //json.put("data", token);
                    response.getWriter().write(json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }else{
                return "redirect:" + req.getRedirect().replaceAll("\\[token\\]", token);

            }

        }

    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ResponseBody
    public UserDetail token(String token) {
        if (token == null) {
            return null;
        }
        UserDetail user = cacheUser.getCacheUser(token);
        cacheUser.removeCacheUser(token);
        return user;
    }

}
