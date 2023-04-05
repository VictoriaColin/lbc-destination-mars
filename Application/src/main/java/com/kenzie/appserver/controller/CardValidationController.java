package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CardValidationRequest;
import com.kenzie.appserver.controller.model.CardValidationResponse;
import com.kenzie.appserver.service.CreditCardService;
import com.kenzie.appserver.service.model.CardValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cardValidator")
public class CardValidationController {
    @Autowired
    private CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<CardValidationResponse> validateCard(@RequestBody CardValidationRequest request) {

        // Validate Credit Card
        CardValidationResult cardValidationResult = creditCardService.validate(request.getCardNumber());

        CardValidationResponse cardValidationResponse = new CardValidationResponse();
        cardValidationResponse.setCardNumberValidationResultCode(cardValidationResult.getCardNumberValidationResultCode());
        cardValidationResponse.setCardAuthorizationNumber(cardValidationResult.getCardAuthorizationNumber());
        cardValidationResponse.setTransactionNumber(cardValidationResult.getTransactionNumber());

        return ResponseEntity.ok(cardValidationResponse);
    }

}




