package com.sayaxat.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Getter
@RequiredArgsConstructor
public class LoginRequest {

    private String email;
    private String password;

}
