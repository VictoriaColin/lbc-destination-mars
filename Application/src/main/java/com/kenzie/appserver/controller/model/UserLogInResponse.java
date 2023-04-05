package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class UserLogInResponse {

    @NotEmpty
    @JsonProperty("logInStatus")
    private String logInStatus;

    public String getLogInStatus() {
        return logInStatus;
    }

    public void setLogInStatus(String logInStatus) {
        this.logInStatus = logInStatus;
    }
}
