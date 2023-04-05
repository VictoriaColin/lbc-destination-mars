package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.LogInValidationResult;
import com.kenzie.appserver.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLogInService {

    private static final String ERROR_CODE = "4000";
    private static final String APPROVAL_CODE = "2000";


    private UserRepository userRepository;

    @Autowired
    public UserLogInService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // we are testing a new email
    public LogInValidationResult validateLogIn(String email, String password){
        List<User> users = new ArrayList<>();
        Iterable<UserRecord> userRecords = userRepository.findAll();
        LogInValidationResult result = new LogInValidationResult();
        //System.out.println("New user email: " + email);

        for (UserRecord record : userRecords) {
            System.out.println("Registered user: " + email);
            boolean isPasswordValid = passwordValidation(email,password);

            // if password is valid but user email and password already exists
            if (record.getUserEmail().equals(email) && isPasswordValid) {
                System.out.println("User Already exists! and password is valid");
                result.setLogInAuthorizationCode(APPROVAL_CODE);
                return result;

            // If password is valid but user does not exict, then add it to the database
            }else if(!record.getUserEmail().equals(email) && isPasswordValid) {
                System.out.println("New user! and password is valid");
                record.setUserEmail(email);
                record.setUserPassword(password);
                record.setLogInAuthorizationCode(APPROVAL_CODE);
                userRepository.save(record);
                result.setLogInAuthorizationCode(APPROVAL_CODE);
                return result;
            }else{
                // if password is not valid
                result.setLogInAuthorizationCode(ERROR_CODE);
                return result;
            }
        }
        return result;
    }

    // load your database
    public User addNewUserDetails(User user){
        UserRecord record = new UserRecord();
        record.setUserEmail(user.getUserEmail());
        record.setUserPassword(user.getUserPassWord());
        userRepository.save(record);
        return user;
    }

    /**
     Rules of Password validation:
     1. length of password should not be greater than 15
     2. length of password should not be less than
     3. password and email should not be same
     4. password should have least 2 upper case letters
     5. password should have least 3 lower case letters
     6. password should have least 1 digit

     */
    public boolean passwordValidation(String email, String password) {
        final int UPPER_COUNT = 2;
        final int LOWER_COUNT = 3;
        final int DIGIT_COUNT = 1;

        int passwordLength = password.length();
        boolean isValid = true;
        int upperCaseCount = 0;
        int lowerCaseCount = 0;
        int digitCount = 0;

        // 1. Length of password >5 <15
        // 2. password not same as email
        if ((password.length() > 15 || password.length() < 5) &&(email.equals(password))) {
            isValid = false;
            return isValid;
        }
        // check that password have least 2 upper, 3 lower, and 1 digit
        for(int i=0;i<password.length();i++) {
            char ch = password.charAt(i);
            if(Character.isUpperCase(ch)){
                upperCaseCount++;
            }else if(Character.isLowerCase(ch)){
                lowerCaseCount++;
            } else if (Character.isDigit(ch)) {
                digitCount++;
            }
        }
        return upperCaseCount >= UPPER_COUNT && lowerCaseCount >= LOWER_COUNT && digitCount >= DIGIT_COUNT;
    }

}

