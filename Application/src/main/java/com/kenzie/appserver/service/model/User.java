package com.kenzie.appserver.service.model;

public class User {
    private String userEmail;
    private String userPassWord;
    private String logInAuthorizationCode;

    public User(String userEmail, String userPassWord, String logInAuthorizationCode) {
        this.userEmail = userEmail;
        this.userPassWord = userPassWord;
        this.logInAuthorizationCode = logInAuthorizationCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getLogInAuthorizationCode() {
        return logInAuthorizationCode;
    }

    public void setLogInAuthorizationCode(String logInAuthorizationCode) {
        this.logInAuthorizationCode = logInAuthorizationCode;
    }
}
