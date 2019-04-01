package com.services.emailmanager.model;

/**
 * @author Sujith Simon
 * Created on : 1/4/19
 */
public class Authentication {
    private String userId;
    private String password;

    public Authentication() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
