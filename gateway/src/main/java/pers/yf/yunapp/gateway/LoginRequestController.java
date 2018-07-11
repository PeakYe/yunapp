package pers.yf.yunapp.gateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yf.spring.cloud.ext.auth.core.LoginRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Controller
//@RequestMapping
public class LoginRequestController {
    @Autowired
    LoginRequestHandler handler;

    @RequestMapping("/login")
    public void handPostReq(HttpServletRequest request, HttpServletResponse response){
        handler.loginPost(request, response);
    }
}
