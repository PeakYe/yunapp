package pers.yf.spring.cloud.ext.auth.route;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import pers.yf.spring.cloud.ext.auth.AuthProperties;

import javax.servlet.http.HttpServletRequest;


public class AutherFilter extends ZuulFilter {

    @Autowired
    public AuthProperties authConfiguration;
    @Autowired
    private UserCacheService userCacheService;


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
        System.out.println("-------------------------------");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String auth = request.getHeader(authConfiguration.getRequestHeader());
        return auth==null;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader(authConfiguration.getRequestHeader());
        // TODO
        String user = userCacheService.getUserDetialJson(token);
        if (user == null) {
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(401);// 返回错误码
            ctx.setResponseBody("token 已经过期");// 返回错误内容
        } else {
            ctx.addZuulRequestHeader(authConfiguration.getForwardHeader(), user);
        }

        return null;
    }
}
