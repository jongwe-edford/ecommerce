package security.auth.service;

import javax.servlet.ServletRequest;

public interface UserLoginService {
    void login(String email, ServletRequest httpServletRequest);
}
