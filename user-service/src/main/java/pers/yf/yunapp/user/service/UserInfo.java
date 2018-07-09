package pers.yf.yunapp.user.service;


import pers.yf.spring.jdbc.lib.annotation.Column;

public class UserInfo {
    @Column("id")
    private Long id;
    @Column("user_name")
    private String userName;
    @Column("password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
