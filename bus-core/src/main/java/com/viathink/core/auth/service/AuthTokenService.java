package com.viathink.core.auth.service;

import com.viathink.core.auth.bean.AuthTokenEntity;
import com.viathink.core.user.dto.LoginForm;

public interface AuthTokenService {
    String login(LoginForm loginForm);
    AuthTokenEntity findAuthTokenById(String id);
    void deleteAuthTokenById(String id);
}
