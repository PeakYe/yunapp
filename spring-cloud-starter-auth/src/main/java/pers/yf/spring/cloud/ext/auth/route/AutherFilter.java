package pers.yf.spring.cloud.ext.auth.route;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.WebUtils;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.core.IUserCacheService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class AutherFilter extends ZuulFilter {
    protected Logger logger = LoggerFactory.getLogger(AutherFilter.class);

    @Autowired
    public AuthProperties authConfiguration;
    @Autowired
    private IUserCacheService userCacheService;
    //    @Autowired
//    private ForwardCache forwardCache;
    private AntPathMatcher matcher = new AntPathMatcher();


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return !unFilter(RequestContext.getCurrentContext().getRequest());
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            Cookie session = null;
            if ("cookie".equals(authConfiguration.getSessionIn())) {
                session = WebUtils.getCookie(request, authConfiguration.getSessionId());
            }
            if (session != null) {
                String user = userCacheService.getUserDetialJson(session.getValue());
                if (user != null && !user.equals("")) {
                    ctx.addZuulRequestHeader(authConfiguration.getForwardHeader(), user);
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //try {
        //    ctx.getResponse().getWriter().write("RE_LOGIN");
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //ctx.setResponseStatusCode(302);
        //ctx.getResponse().setHeader("location", authConfiguration.getAuthService() + authConfiguration.getLoginUrl() + "?redirect=" + URLEncoder.encode(request.getRequestURL().toString()));

        ctx.setResponseStatusCode(401);
        ctx.setSendZuulResponse(false);

        return null;
    }

    protected boolean isLoginRequest(HttpServletRequest request) {
        return request.getRequestURI().equals(authConfiguration.getLoginUrl());
    }

    protected boolean unFilter(HttpServletRequest request) {
        if (authConfiguration.getUnFilter() != null) {
            for (String pattern : authConfiguration.getUnFilter()) {
                if (matcher.match(pattern, request.getRequestURI())) {
                    return true;
                }
            }
        }
        return false;
    }
}
