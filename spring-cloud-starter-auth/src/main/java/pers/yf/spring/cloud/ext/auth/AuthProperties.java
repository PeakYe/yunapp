package pers.yf.spring.cloud.ext.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("templates/auth")
public class AuthProperties {

    private String forwardHeader;
    private String requestHeader;
    private String authService;

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

    public String getAuthService() {
        return authService;
    }

    public void setAuthService(String authService) {
//        if (authService == null) {
//            authService = "auth-service";
//        }
        this.authService = authService;
    }
}
