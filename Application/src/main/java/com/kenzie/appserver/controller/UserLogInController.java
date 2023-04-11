package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.AddUserResponse;
import com.kenzie.appserver.controller.model.UserLogInRequest;
import com.kenzie.appserver.controller.model.UserLogInResponse;
import com.kenzie.appserver.service.UserLogInService;
import com.kenzie.appserver.service.model.LogInValidationResult;
import com.kenzie.appserver.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserLogInController {
    @Autowired
    UserLogInService userLogInService;


    private static final String ERROR_CODE = "4000";
    private static final String APPROVAL_CODE = "2000";


    @PostMapping("/register")
    public ResponseEntity<UserLogInResponse> registerNewUser(@RequestBody UserLogInRequest request) {

        LogInValidationResult logInValidationResult = userLogInService.validateLogIn(request.getUserEmail(), request.getUserPassWord());

        UserLogInResponse response = new UserLogInResponse();
        response.setLogInStatus(logInValidationResult.getLogInAuthorizationCode());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AddUserResponse> addNewUser(@RequestBody UserLogInRequest request) {
        User user = new User(request.getUserEmail(),request.getUserPassWord());

        userLogInService.addNewUserDetails(user);

        AddUserResponse response = new AddUserResponse();
        response.setUserId(UUID.randomUUID().toString());
        response.setUserEmail(user.getUserEmail());
        response.setUserPassword(user.getUserPassWord());

        return ResponseEntity.created(URI.create("/user/" + response.getUserId())).body(response);

    }
}


