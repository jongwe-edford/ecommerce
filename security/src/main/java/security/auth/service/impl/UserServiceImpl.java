package security.auth.service.impl;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import security.auth.config.JwtUtil;
import security.auth.exception.AccountExistException;
import security.auth.exception.PasswordResetTokenExpiredException;
import security.auth.exception.RefreshTokenExpiredException;
import security.auth.exception.UserNotFoundException;
import security.auth.model.PasswordResetToken;
import security.auth.model.RefreshToken;
import security.auth.model.RoleEnum;
import security.auth.model.User;
import security.auth.payload.request.LoginRequest;
import security.auth.payload.request.NewPasswordRequest;
import security.auth.payload.request.RegistrationRequest;
import security.auth.payload.response.LoginResponse;
import security.auth.payload.response.Response;
import security.auth.repository.RoleRepository;
import security.auth.repository.UserRepository;
import security.auth.service.PasswordResetTokenService;
import security.auth.service.RefreshTokenService;
import security.auth.service.UserLoginService;
import security.auth.service.UserService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserLoginService userLoginService;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<Response> registerCustomer(RegistrationRequest registrationRequest, ServletRequest servletRequest) throws AccountExistException {
        if (userRepository.existsByEmail(registrationRequest.email()))
            throw new AccountExistException("An account with the provided email already exist");
        User user = userRepository.save(User
                .builder()
                .country(Locale.getDefault().getDisplayCountry())
                .registrationIp(servletRequest.getRemoteAddr())
                .isCustomer(true)
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .roles(Set.of(roleRepository.findRoleByName(RoleEnum.CUSTOMER)))
                .registrationDate(LocalDateTime.now())
                .build());

        //:TODO: Send welcome email
        //:TODO: Send verification email

        return ResponseEntity.ok(
                new Response("Registration successful", Map.of("user", user), LocalDateTime.now())
        );
    }

    @Override
    public ResponseEntity<Response> registerVendor(RegistrationRequest registrationRequest, ServletRequest servletRequest) throws AccountExistException {
        if (userRepository.existsByEmail(registrationRequest.email()))
            throw new AccountExistException("An account with the provided email already exist");
        User user = userRepository.save(User
                .builder()
                .country(Locale.getDefault().getDisplayCountry())
                .registrationIp(servletRequest.getRemoteAddr())
                .isVendor(true)
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .roles(Set.of(roleRepository.findRoleByName(RoleEnum.VENDOR)))
                .registrationDate(LocalDateTime.now())
                .build());

        //:TODO: Send welcome email
        //:TODO: Send verification email

        return ResponseEntity.ok(
                new Response("Registration successful", Map.of("user", user), LocalDateTime.now())
        );

    }

    @Override
    public ResponseEntity<Response> registerSystemAdmin(RegistrationRequest registrationRequest, ServletRequest servletRequest) throws AccountExistException {
        if (userRepository.existsByEmail(registrationRequest.email()))
            throw new AccountExistException("An account with the provided email already exist");
        String password = passwordEncoder.encode(registrationRequest.password());
        System.out.println("Encoded password:::" + password);
        User user = userRepository.save(User
                .builder()
                .country(Locale.getDefault().getDisplayCountry())
                .registrationIp(servletRequest.getRemoteAddr())
                .isSysAdmin(true)
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .roles(Set.of(roleRepository.findRoleByName(RoleEnum.SYSADMIN)))
                .registrationDate(LocalDateTime.now())
                .build());

        //:TODO: Send welcome email
        //:TODO: Send verification email

        return ResponseEntity.ok(
                new Response("Registration successful", Map.of("user", user), LocalDateTime.now())
        );
    }

    @Override
    public ResponseEntity<Response> login(LoginRequest loginRequest, ServletRequest servletRequest) {
        ResponseEntity<Response> responseEntity;
        try {
            User user2 = userRepository.findUserByEmail(loginRequest.email()).orElseThrow(() -> new UserNotFoundException("No account with the provided email"));
            if (!passwordEncoder.matches(loginRequest.password(), user2.getPassword()))
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(new Response("Invalid password", null, LocalDateTime.now()));
            if (!user2.isEnabled()) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(new Response("You need to verify your email address before you start using the app", null, LocalDateTime.now()));
            }
            Authentication authentication = manager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.email(), loginRequest.password()
                            )
                    );
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
            userRepository.updateLastLoginTime(loginRequest.email(), LocalDateTime.now());
            userLoginService.login(loginRequest.email(), servletRequest);
            ResponseCookie responseCookie = jwtUtil.generateJwtCookie(user2);
            LoginResponse loginResponse = new LoginResponse(responseCookie.getValue(), refreshTokenService.saveToken(loginRequest.email()).getToken());
            responseEntity = ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(new Response("Login successful", Map.of("token", loginResponse), LocalDateTime.now()));
        } catch (BadCredentialsException | ExpiredJwtException e) {
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(new Response("Invalid email or password", null, LocalDateTime.now()));
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<Response> refreshToken(String token) throws RefreshTokenExpiredException {
        RefreshToken refreshToken = refreshTokenService.findRefreshTokenByToken(token);
        if (!refreshTokenService.validateToken(refreshToken))
            throw new RefreshTokenExpiredException("Token expired");
        ResponseCookie responseCookie = jwtUtil.generateJwtCookie(refreshToken.getUser());
        LoginResponse loginResponse = new LoginResponse(
                responseCookie.getValue(),
                refreshTokenService.saveToken(refreshToken.getUser().getEmail()).getToken()
        );
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new Response("Login successful", Map.of("token", loginResponse), LocalDateTime.now()));


    }

    @Override
    public Response forgotPassword(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            PasswordResetToken passwordResetToken = passwordResetTokenService.generateToken(email);
            log.info("Password reset token: {}", passwordResetToken.toString());
            return new Response("Password reset email sent successfully", null, LocalDateTime.now());
        }
        return null;


    }

    @Override
    public Response resetPassword(String token, NewPasswordRequest newPasswordRequest) throws PasswordResetTokenExpiredException {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findPasswordResetTokenByToken(token);
        if (!passwordResetTokenService.validateToken(passwordResetToken))
            throw new PasswordResetTokenExpiredException("Token expired");

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPasswordRequest.password()));
        userRepository.save(user);
        return new Response("Password reset successful", null, LocalDateTime.now());
    }

    @Override
    public User getCurrentUserByToken(String token) {
        String email = jwtUtil.getEmailFromToken(token);
        return userRepository.findByEmail(email);
    }
}
