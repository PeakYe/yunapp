package pers.yf.yunapp.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserInfoController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/registe")
    public Long registe(String userName, String password) {
        UserInfo info = new UserInfo();
        info.setUserName(userName);
        info.setPassword(password);
        userDao.insert(info);

        System.out.println(info.getId());
        return info.getId();
    }

    @RequestMapping("/get")
    public UserInfo get(Long userID) {
        UserInfo info = userDao.selectById(userID);
        return info;
    }

    @RequestMapping("/getAll")
    public List<UserInfo> get() {
        return userDao.getAllUser();
    }

}
