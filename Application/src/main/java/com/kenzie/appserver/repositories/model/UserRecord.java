package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "User")
public class UserRecord {

    private String userId;
    private String userEmail;
    private String userPassword;
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", logInAuthorizationCode=" + logInAuthorizationCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRecord)) return false;
        UserRecord that = (UserRecord) o;
        return logInAuthorizationCode == that.logInAuthorizationCode && userId.equals(that.userId) && userEmail.equals(that.userEmail) && userPassword.equals(that.userPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userEmail, userPassword, logInAuthorizationCode);
    }
}
