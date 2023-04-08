package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.LogInValidationResult;
import com.kenzie.appserver.service.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UserLogInValidationTest {
    @InjectMocks
    private UserLogInService subject;

    private UserRepository userRepository;

    @BeforeEach
    void setuo(){
        userRepository = mock(UserRepository.class);
        subject = new UserLogInService(userRepository);
    }

    @Test
    void validateLogin_isSuccessful(){

        String email1= "xyz.whatever.com";
        String password1 = "YTabc3";
        LogInValidationResult result = new LogInValidationResult();
        result.setLogInAuthorizationCode("2000");

        UserRecord record1 = new UserRecord();
        record1.setUserEmail(email1);
        record1.setUserPassword(password1);
        record1.setLogInAuthorizationCode("2000");

        String email2= "abc.whatever.com";
        String password2 = "SJabc3";

        UserRecord record2 = new UserRecord();
        record1.setUserEmail(email2);
        record1.setUserPassword(password2);

        List<UserRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);

        when(userRepository.findAll()).thenReturn(recordList);

        LogInValidationResult actualResult = subject.validateLogIn(email2, password2);


        assertNotNull(recordList, "The user list is returned");
        Assertions.assertEquals(2, recordList.size(), "There are two users");
        Assertions.assertEquals(result.getLogInAuthorizationCode(),actualResult.getLogInAuthorizationCode());

    }
    @Test
    void validateLogin_UnSuccessful(){

        String email1= "xyz.whatever.com";
        String password1 = "YTabc3";
        LogInValidationResult result = new LogInValidationResult();
        result.setLogInAuthorizationCode("4000");

        UserRecord record1 = new UserRecord();
        record1.setUserEmail(email1);
        record1.setUserPassword(password1);
        record1.setLogInAuthorizationCode("2000");

        String email2= "abc.whatever.com";
        String password2 ="YTabcc";;

        record1.setUserEmail(email2);
        record1.setUserPassword(password2);

        List<UserRecord> recordList = new ArrayList<>();
        recordList.add(record1);

        when(userRepository.findAll()).thenReturn(recordList);

        LogInValidationResult actualResult = subject.validateLogIn(email2, password2);


        assertNotNull(recordList, "The user list is returned");
        Assertions.assertEquals(1, recordList.size(), "There is only one users");
        Assertions.assertEquals(result.getLogInAuthorizationCode(),actualResult.getLogInAuthorizationCode());

    }
    @Test
    void addUser(){
        String email = "xyz.whatever.com";
        String password = "YTabc3";
        User user = new User(email,password);
        User returnedUser = subject.addNewUserDetails(user);

        ArgumentCaptor<UserRecord> userRecordCaptor = ArgumentCaptor.forClass(UserRecord.class);

        assertNotNull(returnedUser);
        verify(userRepository).save(userRecordCaptor.capture());
        UserRecord record = userRecordCaptor.getValue();

        assertNotNull(record, "The user record is returned");
        Assertions.assertEquals(record.getUserEmail(), user.getUserEmail(), "The email id matches");
        Assertions.assertEquals(record.getUserPassword(), user.getUserPassWord(), "The password matches");

    }

    @Test
    void isPasswordValid(){
        // Given
        String email = "xyz.whatever.com";
        String password = "YTabc3";

        // When
        Boolean validate = subject.passwordValidation(email,password);

        //Then
        Assertions.assertEquals(true,validate);
    }
    @Test
    public void isPasswordInValid_doesNotHaveDigit(){
        String email = "xyz.whatever.com";
        String password = "YTabcc";

        Boolean validate = subject.passwordValidation(email,password);

        Assertions.assertEquals(false,validate);

    }

    @Test
    public void isPasswordInValid_emailPasswordSame(){
        // Given
        String email = "xyz.whatever.com";
        String password = "xyz.whatever.com";

        // When
        Boolean validate = subject.passwordValidation(email,password);

        //Then
        Assertions.assertEquals(false,validate);
    }

    @Test
    public void isPasswordInValid_passwordLengthGreaterThan15(){
        // Given
        String email = "xyz.whatever.com";
        String password = "xyz.whatever.com";

        // When
        Boolean validate = subject.passwordValidation(email,password);

        //Then
        Assertions.assertEquals(false,validate);
    }

    @Test
    public void isPasswordInValid_passwordHasUpperCase(){
        // Given
        String email = "xyz.whatever.com";
        String password = "XYzxyz5";

        // When
        Boolean validate = subject.passwordValidation(email,password);

        //Then
        Assertions.assertEquals(true,validate);
    }

    @Test
    public void isPasswordInValid_passwordLengthLessThan5(){
        // Given
        String email = "xyz.whatever.com";
        String password = "xyz";

        // When
        Boolean validate = subject.passwordValidation(email,password);

        //Then
        Assertions.assertEquals(false,validate);
    }

}
