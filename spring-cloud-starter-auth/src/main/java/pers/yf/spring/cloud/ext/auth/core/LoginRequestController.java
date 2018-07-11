package pers.yf.spring.cloud.ext.auth.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginRequestController {
    @Autowired
    LoginRequestHandler handler;

    @RequestMapping("/login")
    public void handPostReq(HttpServletRequest request, HttpServletResponse response){
        handler.loginPost(request, response);
    }
}
