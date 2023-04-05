package com.kenzie.appserver.service.model;

public class CardValidationResult {
    private String transactionNumber;
    private String cardAuthorizationNumber;
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
