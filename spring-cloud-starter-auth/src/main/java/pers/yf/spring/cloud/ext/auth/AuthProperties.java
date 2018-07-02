package pers.yf.spring.cloud.ext.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("auth")
public class AuthProperties {

    private String forwardHeader;
    private String requestHeader;
    private String loginUrl;
    private String sessionId;
    private String sessionIn;
    private String tokenUrl;
    private String authService;
    private List<String> unFilter;

    public String getForwardHeader() {
        if (forwardHeader == null) {
            return "user";
        }
        return forwardHeader;
    }

    public void setForwardHeader(String forwardHeader) {
        this.forwardHeader = forwardHeader;
    }

    public String getRequestHeader() {
//        if (requestHeader == null) {
//            return "token";
//        }
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setLoginUrl(String loginUrl) {

        this.loginUrl = loginUrl;
    }

    public String getSessionIn() {
        return sessionIn;
    }

    public void setSessionIn(String sessionIn) {
        this.sessionIn = sessionIn;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getAuthService() {
        return authService;
    }

    public void setAuthService(String authService) {
        this.authService = authService;
    }

    public List<String> getUnFilter() {
        return unFilter;
    }

    public void setUnFilter(List<String> unFilter) {
        this.unFilter = unFilter;
    }
}
