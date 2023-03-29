package com.kenzie.appserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.mock;

public class CreditCardServiceTest {

    @InjectMocks
    private CreditCardService subject;

    @BeforeEach
    void setup(){
        subject = new CreditCardService();
    }

    @Test
    public void isCardValid(){
        // Given
        String creditCardString = "79927398713";
        // When
        boolean isValid = subject.validate(creditCardString);
        //Then
        Assertions.assertTrue(isValid);
    }

    @Test
    public void isCardInValid(){
        // Given
        String creditCardString = "79927389713";
        // When
        boolean isValid = subject.validate(creditCardString);
        //Then
        Assertions.assertFalse(isValid);
    }




}
