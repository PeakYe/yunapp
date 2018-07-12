package pers.yf.yunapp.gateway.auth;

import pers.yf.spring.cloud.ext.auth.UserDetail;
import pers.yf.spring.cloud.ext.auth.core.IUserManager;

public class UserManager implements IUserManager {
    @Override
    public UserDetail getUserByName(String name) {
        return null;
    }

    @Override
    public boolean validatePassword(UserDetail userDetail, String newPwd) {
        return false;
    }
}
