package pers.yf.spring.cloud.ext.auth.client;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pers.yf.spring.cloud.ext.auth.AuthProperties;
import pers.yf.spring.cloud.ext.auth.UserDetail;

@EnableConfigurationProperties(AuthProperties.class)
public class AuthUserResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private AuthProperties authConfiguration;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(UserDetail.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String header = nativeWebRequest.getHeader(authConfiguration.getForwardHeader());
        UserDetail user = new UserDetail();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(header);
            user.setId(jsonObject.getString("id"));
            user.setUserName(jsonObject.getString("userName"));
        } catch (JSONException e) {
            System.out.println(header);
            e.printStackTrace();
        }
        return user;
    }
}
