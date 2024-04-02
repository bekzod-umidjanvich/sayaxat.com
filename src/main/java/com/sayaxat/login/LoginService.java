package com.sayaxat.login;

import com.sayaxat.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserService appUserService;

    public String login(LoginRequest request) {
        appUserService.login(request);
        return "login works";
    }
}
