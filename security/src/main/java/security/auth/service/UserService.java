package security.auth.service;


import org.springframework.http.ResponseEntity;
import security.auth.exception.AccountExistException;
import security.auth.exception.PasswordResetTokenExpiredException;
import security.auth.exception.RefreshTokenExpiredException;
import security.auth.exception.UserNotFoundException;
import security.auth.model.User;
import security.auth.payload.request.LoginRequest;
import security.auth.payload.request.NewPasswordRequest;
import security.auth.payload.request.RegistrationRequest;
import security.auth.payload.response.Response;

import javax.servlet.ServletRequest;

public interface UserService {
    ResponseEntity<Response> registerCustomer(RegistrationRequest registrationRequest, ServletRequest servletRequest) throws AccountExistException;

    ResponseEntity<Response> registerVendor(RegistrationRequest registrationRequest, ServletRequest servletRequest) throws AccountExistException;

    ResponseEntity<Response> registerSystemAdmin(RegistrationRequest registrationRequest, ServletRequest servletRequest) throws AccountExistException, AccountExistException;

    ResponseEntity<Response> login(LoginRequest loginRequest, ServletRequest servletRequest);

    ResponseEntity<Response> refreshToken(String token) throws RefreshTokenExpiredException;

    Response forgotPassword(String email) throws UserNotFoundException;

    Response resetPassword(String token, NewPasswordRequest newPasswordRequest) throws PasswordResetTokenExpiredException;

    User getCurrentUserByToken(String token);
}
