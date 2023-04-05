package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.UserLogInRequest;
import com.kenzie.appserver.controller.model.UserLogInResponse;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    UserRepository userLogInService;
//
//    @Autowired
//    UserRepository userRepository;
//
//
//    @PostMapping("/register")
//    public ResponseEntity<List<UserLogInResponse>> registerNewUser(@RequestBody UserLogInRequest request){
//
////        List<User> users = userLogInService.getAllUsers();
////
////        return ResponseEntity.ok(logInResponse);
//    }
//
//
//
//}
