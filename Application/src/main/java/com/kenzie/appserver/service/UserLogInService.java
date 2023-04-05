package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLogInService {

    private static final String ERROR_CODE = "4000";
    private static final String APPROVAL_CODE = "2000";

    UserRepository userRepository;

    @Autowired
    public UserLogInService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // we are testing a new email
    public List<User>findAllUsers(String email){
        List<User> users = new ArrayList<>();
        Iterable<UserRecord> userRecords = userRepository.findAll();
        System.out.println("New user email: " + email);

        for (User user : users) {
            System.out.println("Registered user: " + email);
            if (user.getUserEmail().equals(email)) {
                System.out.println("User Already exists!");
                UserRecord userRecord = new UserRecord();
                userRecord.setUserEmail(user.getUserEmail());
                userRecord.setUserPassword(user.getUserPassWord());
                userRecord.setLogInAuthorizationCode(APPROVAL_CODE);
                userRepository.save(userRecord);
                //return ;
            }
        }

        return null;
    }

    }

