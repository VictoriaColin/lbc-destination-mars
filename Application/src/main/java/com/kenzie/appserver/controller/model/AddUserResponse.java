package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddUserResponse {

    @NotEmpty
    @JsonProperty("userId")
    private String userId;

    @NotEmpty
    @JsonProperty("userEmail")
    private String userEmail;

    @NotEmpty
    @JsonProperty("userPassword")
    private String userPassword;

    @NotEmpty
    @JsonProperty("logInAuthorizationCode")
    private String logInAuthorizationCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getLogInAuthorizationCode() {
        return logInAuthorizationCode;
    }

    public void setLogInAuthorizationCode(String logInAuthorizationCode) {
        this.logInAuthorizationCode = logInAuthorizationCode;
    }
}
