package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardValidationResponse {

    @NotEmpty
    @JsonProperty("transactionNumber")
    private String transactionNumber;

    @NotEmpty
    @JsonProperty("cardAuthorizationNumber")
    private String cardAuthorizationNumber; // card approval code

    @NotEmpty
    @JsonProperty("cardNumberValidationResultCode")
    private String cardNumberValidationResultCode;

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getCardAuthorizationNumber() {
        return cardAuthorizationNumber;
    }

    public void setCardAuthorizationNumber(String cardAuthorizationNumber) {
        this.cardAuthorizationNumber = cardAuthorizationNumber;
    }

    public String getCardNumberValidationResultCode() {
        return cardNumberValidationResultCode;
    }

    public void setCardNumberValidationResultCode(String cardNumberValidationResultCode) {
        this.cardNumberValidationResultCode = cardNumberValidationResultCode;
    }
}
