package pers.yf.spring.cloud.ext.auth.core;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.UserDetail;
import pers.yf.spring.cloud.ext.auth.server.ServerUserCache;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;


public class LoginRequestHandler {

    @Autowired
    ServerUserCache cacheUser;

    @Autowired
    IUserManager userManager;

    @Autowired
    AuthProperties properties;


    //    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, HttpServletResponse response) {
        String value = getRequestPayload(request);
        JSONObject jsonReq = new JSONObject(value);
        LoginReq req = new LoginReq();
        req.setUsername(jsonReq.getString("username"));
        req.setPassword(jsonReq.getString("password"));
        UserDetail user = userManager.getUserByName(req.getUsername());
        boolean validate = false;
        String token = null;
        if (user == null) {
            validate = false;
        } else {
            if (userManager.validatePassword(user, req.getPassword())) {
                token = UUID.randomUUID().toString();
                cacheUser.cacheUser(token, user);
                //response.setHeader(properties.getForwardHeader(), token);
                //response.addCookie(new Cookie("token", token));
                validate = true;
            }

        }

        if (validate) {
            try {
                JSONObject json = new JSONObject();
                json.put("code", "ok");
                json.put("token", token);
                response.getWriter().write(json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        } else {
            try {
                JSONObject json = new JSONObject();
                json.put("code", "valid_err");
                //json.put("data", token);
                response.getWriter().write(json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

    }


    private String getRequestPayload(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
