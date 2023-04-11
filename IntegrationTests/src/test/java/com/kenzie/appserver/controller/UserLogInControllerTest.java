package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.SeatReservationResponse;
import com.kenzie.appserver.controller.model.UserLogInRequest;
import com.kenzie.appserver.service.FlightService;
import com.kenzie.appserver.service.UserLogInService;
import com.kenzie.appserver.service.model.User;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class UserLogInControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserLogInService userLogInService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addNewUser_AdditionSuccessful() throws Exception {
        String email = "abc@whateveremail.com";
        String password = "ABcdef3";
        User newUser = new User(email,password);

        userLogInService.addNewUserDetails(newUser);

        UserLogInRequest request = new UserLogInRequest();
        request.setUserEmail(newUser.getUserEmail());
        request.setUserPassWord(newUser.getUserPassWord());

        mvc.perform(post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(jsonPath("userEmail")
                        .value(is(email)))
                .andExpect(jsonPath("userPassword")
                        .value(is(password)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void registerNewUser_AdditionSuccessful() throws Exception {

        String email = "abc@whateveremail.com";
        String password = "ABcdef3";
        User newUser = new User(email,password);

        userLogInService.validateLogIn(email,password);

        UserLogInRequest request = new UserLogInRequest();
        request.setUserEmail(newUser.getUserEmail());
        request.setUserPassWord(newUser.getUserPassWord());

        ResultActions actions = mvc.perform(
                post("/user/register/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void registerNewUser_userExists() throws Exception {

        String email = "abc@whateveremail.com";
        String password = "ABcdef3";
        User newUser = new User(email,password);

        userLogInService.validateLogIn(email,password);

        UserLogInRequest request = new UserLogInRequest();
        request.setUserEmail(newUser.getUserEmail());
        request.setUserPassWord(newUser.getUserPassWord());
        mvc.perform(post("/user/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
