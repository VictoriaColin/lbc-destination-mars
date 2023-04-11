package com.kenzie.appserver.service.model;

public class User {
    private String userEmail;
    private String userPassWord;

    public User(String userEmail, String userPassWord) {
        this.userEmail = userEmail;
        this.userPassWord = userPassWord;

    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    }
