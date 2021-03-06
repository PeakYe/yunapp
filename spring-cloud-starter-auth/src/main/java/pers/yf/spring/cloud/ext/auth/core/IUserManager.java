package pers.yf.spring.cloud.ext.auth.core;

import pers.yf.spring.cloud.ext.auth.UserDetail;

public interface IUserManager {

    UserDetail getUserByName(String name);

    boolean validatePassword(UserDetail userDetail, String newPwd);
}