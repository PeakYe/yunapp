package pers.yf.spring.cloud.ext.auth.server;

import pers.yf.spring.cloud.ext.auth.UserDetail;
import pers.yf.spring.cloud.ext.auth.core.IUserManager;

public class DefaultUserManger implements IUserManager {
    @Override
    public UserDetail getUserByName(String name) {
        if (name.equals("admin")) {
            UserDetail detail = new UserDetail();
            detail.setUserName("admin");
            detail.setPassword("123456");
            detail.setId("0");
            return detail;
        }
        return null;
    }

    @Override
    public boolean validatePassword(UserDetail detail, String newPwd) {
        return detail.getPassword().equals(newPwd);
    }
}
