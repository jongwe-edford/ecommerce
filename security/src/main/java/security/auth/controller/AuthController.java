package security.auth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.auth.exception.AccountExistException;
import security.auth.exception.PasswordResetTokenExpiredException;
import security.auth.exception.RefreshTokenExpiredException;
import security.auth.model.User;
import security.auth.payload.request.LoginRequest;
import security.auth.payload.request.NewPasswordRequest;
import security.auth.payload.request.RegistrationRequest;
import security.auth.payload.response.Response;
import security.auth.service.UserService;

import javax.servlet.ServletRequest;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping(path = "login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest, ServletRequest servletRequest) {
        return userService.login(loginRequest, servletRequest);
    }

    @PostMapping(path = "register/customer")
    public ResponseEntity<Response> registerCustomer(@RequestBody RegistrationRequest loginRequest, ServletRequest servletRequest) throws AccountExistException {
        return userService.registerCustomer(loginRequest, servletRequest);
    }

    @PostMapping(path = "register/vendor")
    public ResponseEntity<Response> registerVendor(@RequestBody RegistrationRequest loginRequest, ServletRequest servletRequest) throws AccountExistException {
        return userService.registerVendor(loginRequest, servletRequest);
    }

    @PostMapping(path = "register/sys-admin")
    public ResponseEntity<Response> registerSystemAdmin(@RequestBody RegistrationRequest loginRequest, ServletRequest servletRequest) throws AccountExistException {
        return userService.registerSystemAdmin(loginRequest, servletRequest);
    }

    @PostMapping(path = "refresh")
    public ResponseEntity<Response> refreshAuthToken(@RequestParam("token") String token) throws RefreshTokenExpiredException {
        return userService.refreshToken(token);
    }

    @PostMapping(path = "forgot")
    public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) {
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }

    @PostMapping(path = "reset-password")
    public ResponseEntity<Response> resetPassword(@RequestParam("token") String token, @RequestBody NewPasswordRequest newPasswordRequest) throws PasswordResetTokenExpiredException {
        return new ResponseEntity<>(userService.resetPassword(token, newPasswordRequest), HttpStatus.OK);
    }

    @GetMapping(path = "current-user")
    public ResponseEntity<User> getCurrentUser(@RequestParam(value = "token") String token) {
        return new ResponseEntity<>(userService.getCurrentUserByToken(token), HttpStatus.OK);
    }
}
