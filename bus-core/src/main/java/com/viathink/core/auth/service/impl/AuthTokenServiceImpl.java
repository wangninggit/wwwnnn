package com.viathink.core.auth.service.impl;

import com.viathink.core.auth.bean.AuthTokenEntity;
import com.viathink.core.auth.mapper.AuthTokenMapper;
import com.viathink.core.auth.service.AuthTokenService;
import com.viathink.core.user.bean.UserEntity;
import com.viathink.core.user.dto.LoginForm;
import com.viathink.core.user.mapper.UserMapper;
import com.viathink.core.util.CryptoUtil;
import com.viathink.core.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {
    private final UserMapper userMapper;
    private final AuthTokenMapper authTokenMapper;
    @Value("${sys.token.secret}")
    private String secret;
    @Value("${sys.token.jwt-expire-time}")
    private int jwtExpireTime;

    @Autowired
    public AuthTokenServiceImpl(UserMapper userMapper, AuthTokenMapper authTokenMapper) {
        this.userMapper = userMapper;
        this.authTokenMapper = authTokenMapper;
    }

    @Override
    public String login(LoginForm loginForm) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(loginForm.getEmail());
        userEntity.setPassword("");
        UserEntity user = userMapper.findByEmail(loginForm.getEmail());
        if (user == null) {
            return "userNotFound";
        }
        String password = CryptoUtil.sha1Hash(loginForm.getPassword(), user.getSalt());
        if (!password.equals(user.getPassword())) {
            return "passwordError";
        }
        if (!user.getActivate()) {
            return "userIsNotActivate";
        }
        // 创建token
        String tokenId = CryptoUtil.getRandomSalt();
        AuthTokenEntity authTokenEntity = new AuthTokenEntity();
        authTokenEntity.setId(tokenId);
        authTokenEntity.setUserId(String.valueOf(user.getId()));
        authTokenMapper.addAuthToken(authTokenEntity);
        return JWTUtil.sign(String.valueOf(user.getId()), tokenId, secret, jwtExpireTime);
    }

    @Override
    public AuthTokenEntity findAuthTokenById(String id) {
        return authTokenMapper.findAuthTokenById(id);
    }

    @Override
    public void deleteAuthTokenById(String id) {
        AuthTokenEntity authTokenEntity = authTokenMapper.findAuthTokenById(id);
        if (authTokenEntity != null) {
            authTokenMapper.deleteAuthTokenById(id);
        }
    }
}
