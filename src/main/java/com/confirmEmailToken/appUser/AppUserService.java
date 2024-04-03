package com.confirmEmailToken.appUser;

import com.confirmEmailToken.login.LoginRequest;
import com.confirmEmailToken.registration.token.ConfirmationToken;
import com.confirmEmailToken.registration.token.ConfirmationTokenService;
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

        //TODO: Validate Email
        if (appUserRepository
                .findAppUserByEmail(appUser.getEmail())
                .isPresent()
        ) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        //TODO: Save User to the database
        appUserRepository.save(appUser);

        //TODO: Create token
        String token = UUID.randomUUID().toString();

        //TODO: Save token to the database which is related to the user
        confirmationTokenService.saveConfirmationToken(new ConfirmationToken(
                token,
                java.time.LocalDateTime.now(),
                java.time.LocalDateTime.now().plusMinutes(15),
                appUser
        ));

        //TODO: Confirm token is Created and return token
        return token;
    }

    //TODO: Login
    public void login(LoginRequest request) {

        //check if email and password are correct
        AppUser appUser = appUserRepository.findAppUserByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("email not found"));

        if (!BCrypt.checkpw(request.getPassword(), appUser.getPassword())) {
            throw new IllegalStateException("password incorrect");
        }


    }


    public void enableAppUser(String email) {
        AppUser appUser = appUserRepository.findAppUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));

        appUser.setEnabled(true);

        appUserRepository.save(appUser);
    }
}
