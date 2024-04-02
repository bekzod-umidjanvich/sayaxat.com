package com.sayaxat.appUser;

import com.sayaxat.login.LoginRequest;
import com.sayaxat.registration.token.ConfirmationToken;
import com.sayaxat.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findAppUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findAppUserByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        confirmationTokenService.saveConfirmationToken(new ConfirmationToken(
                token,
                java.time.LocalDateTime.now(),
                java.time.LocalDateTime.now().plusMinutes(15),
                appUser
        ));

        //TODO: Send email

        return token;
    }

    //Login method
    public void login(LoginRequest request) {
        appUserRepository.findAppUserByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        System.err.println("Kirdi login method");
    }


    public void enableAppUser(String email) {
        AppUser appUser = appUserRepository.findAppUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));

        appUser.setEnabled(true);

        appUserRepository.save(appUser);
    }
}
