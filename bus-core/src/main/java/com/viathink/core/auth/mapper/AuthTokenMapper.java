package com.viathink.core.auth.mapper;

import com.viathink.core.auth.bean.AuthTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenMapper {
    void addAuthToken(AuthTokenEntity tokenEntity);
    void deleteAuthTokenById(String id);
    AuthTokenEntity findAuthTokenById(String id);
}
