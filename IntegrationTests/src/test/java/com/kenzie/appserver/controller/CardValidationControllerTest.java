package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CardValidationRequest;
import com.kenzie.appserver.controller.model.CardValidationResponse;
import com.kenzie.appserver.controller.model.ReservedTicketResponse;
import com.kenzie.appserver.service.CreditCardService;
import com.kenzie.appserver.service.model.Card;
import com.kenzie.appserver.service.model.CardValidationResult;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.apache.http.client.methods.RequestBuilder.post;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class CardValidationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CreditCardService creditCardService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void cardValidation_Successful() throws Exception {

        String cardNumber = "378282246310005";

        creditCardService.validate(cardNumber);

        CardValidationRequest request = new CardValidationRequest();
        request.setCardNumber(cardNumber);

        mapper.registerModule(new JavaTimeModule());

        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/cardValidator")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();
        CardValidationResponse response = mapper.readValue(responseBody, CardValidationResponse.class);

        assertThat(creditCardService.validate(cardNumber))
                .isNotNull();




    }





}
