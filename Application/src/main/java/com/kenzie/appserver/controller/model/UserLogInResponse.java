package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class UserLogInResponse {

    @NotEmpty
    @JsonProperty("logInStatus")
    private String logInStatus;

    @NotEmpty
    @JsonProperty("logInAuthorizationNumber")
    private String logInAuthorizationNumber; // card approval code

    public String getLogInStatus() {
        return logInStatus;
    }

    public void setLogInStatus(String logInStatus) {
        this.logInStatus = logInStatus;
    }

    public String getLogInAuthorizationNumber() {
        return logInAuthorizationNumber;
    }

    public void setLogInAuthorizationNumber(String logInAuthorizationNumber) {
        this.logInAuthorizationNumber = logInAuthorizationNumber;
    }
}
