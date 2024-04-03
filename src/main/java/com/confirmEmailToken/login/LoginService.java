package com.confirmEmailToken.login;

import com.confirmEmailToken.appUser.AppUserService;
import com.confirmEmailToken.registration.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String login(LoginRequest request) {

        appUserService.login(request);

        return "login works";
    }
}
