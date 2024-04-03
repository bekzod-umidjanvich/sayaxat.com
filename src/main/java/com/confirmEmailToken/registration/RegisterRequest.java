package com.confirmEmailToken.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Getter
@RequiredArgsConstructor
public class RegisterRequest {


    private String email;
    private String password;

}
