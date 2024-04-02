package com.sayaxat.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

     @PostMapping
     public String login(@RequestBody LoginRequest request) {
         System.out.println("request.getPassword() = " + request.getPassword());
         return loginService.login(request);
     }

    // @GetMapping("/confirm")
    // public String confirm(@RequestParam("token") String token) {
    //     return loginService.confirmToken(token);
    // }

    // @GetMapping("/logout")
    // public String logout(@RequestParam("token") String token) {
    //     return loginService.logout(token);
    // }

    // @GetMapping("/forgot-password")
    // public String forgotPassword(@RequestParam("email") String email) {
    //     return loginService.forgotPassword(email);
    // }

    // @GetMapping("/reset-password")
    // public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
    //     return loginService.resetPassword(token, password);
    // }

    // @GetMapping("/change-password")
    // public String changePassword(@RequestParam("token") String token, @RequestParam("password") String password) {
    //     return loginService.changePassword(token, password);
    // }

    // @GetMapping("/change-email")
    // public String changeEmail(@RequestParam("token") String token, @RequestParam("email") String email) {
    //     return loginService.changeEmail(token, email);
    // }




}
