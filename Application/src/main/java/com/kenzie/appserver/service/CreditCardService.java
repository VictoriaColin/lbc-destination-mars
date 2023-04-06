package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.CardValidationResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 1. number of digits is > 13 <= 16.
 * Visa cards start with 4
 * Master cards start with 5
 * American Express cards start with 37
 * Discover cards start with 6
 *
 * https://www.geeksforgeeks.org/luhn-algorithm/
 */
@Service
public class CreditCardService {
    private static final String ERROR_CODE = "4000";
    private static final String APPROVAL_CODE = "2000";


    public CardValidationResult validate(String creditCardString){

        System.out.println("isCreditCardNumberValid.....");

        //1. Convert cardNumber from String to an int
        int[] creditCardNumber = new int[creditCardString.length()];

        // 2. add all values from String to the array
        for (int i = 0; i < creditCardString.length(); i++) {
            creditCardNumber[i] = Integer.parseInt(creditCardString.substring(i,i+1));
        }

        // 3. Starting from right to left,
        // -2 because we want to skip out final element
        // and also deal only with every other element of the credit card
        for(int i = creditCardNumber.length-2; i >= 0; i= i-2){
            int tempVal = creditCardNumber[i];
            // we need to double every other number
            tempVal = tempVal * 2;
            // if greater than 9, we do not want it to be a double-digit
            if(tempVal > 9){
                // this will make sure that the digit is below 9
                tempVal = (tempVal % 10) + 1;
            }
            // set the current digit to temp value
            creditCardNumber[i] = tempVal;
        }

        //4. Add of all digits
        int totalValueOfDigits = 0;
        for (int i = 0; i < creditCardNumber.length; i++) {
            totalValueOfDigits+= creditCardNumber[i];
        }

        //5. if total is a multiple of 10, it is valid
        if(totalValueOfDigits % 10 ==0){
            System.out.println("true");
            CardValidationResult result = new CardValidationResult();
            result.setCardAuthorizationNumber(UUID.randomUUID().toString());
            result.setCardNumberValidationResultCode(APPROVAL_CODE);
            result.setTransactionNumber(UUID.randomUUID().toString());
            return result;
        }
        CardValidationResult result = new CardValidationResult();
        result.setCardNumberValidationResultCode(ERROR_CODE);
        System.out.println("false");
        return result;
    }

}
