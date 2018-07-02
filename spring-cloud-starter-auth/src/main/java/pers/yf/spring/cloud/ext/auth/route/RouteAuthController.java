package pers.yf.spring.cloud.ext.auth.route;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.UserDetail;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("${auth.route.accept.path:acceptLogin}")
public class RouteAuthController {

    protected Logger logger = LoggerFactory.getLogger(RouteAuthController.class);

    @Autowired
    IUserCacheService userCacheService;
    @Autowired
    AuthProperties authProperties;
    @Autowired
    ForwardCache forwardCache;

    @RequestMapping(produces = {"text/html"})
    public String acceptLogin(String token, HttpServletRequest request) {
        UserDetail userDetail = userCacheService.getUserDetail(token);
        if (userDetail == null) {
            logger.error("token error:" + token);
            return "redirect:" + authProperties.getLoginUrl();
        } else {
            Cookie session = WebUtils.getCookie(request, "SESSION");
            String forward = forwardCache.getCache(session.getValue());
            if (!StringUtils.isEmpty(forward)) {
                return "redirect:" + forward;
            }
            return null;
        }
    }
}
