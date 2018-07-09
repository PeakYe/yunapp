package pers.yf.spring.cloud.ext.auth.route;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.core.LoginRequestHandler;

import javax.servlet.http.HttpServletRequest;

public class AutherLoginFilter extends ZuulFilter {

    @Autowired
    public AuthProperties authConfiguration;

    @Autowired
    private LoginRequestHandler loginRequestHandler;

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
        return RequestContext.getCurrentContext().getRequest().getRequestURI().equals("/login");
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        loginRequestHandler.loginPost(request, ctx.getResponse());

        ctx.setSendZuulResponse(false);
        return null;
    }
}
