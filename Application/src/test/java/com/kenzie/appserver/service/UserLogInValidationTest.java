package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.mock;

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
    public void isPasswordValid(){
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
        // Given
        String email = "xyz.whatever.com";
        String password = "YTabcc";

        // When
        Boolean validate = subject.passwordValidation(email,password);

        //Then
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
