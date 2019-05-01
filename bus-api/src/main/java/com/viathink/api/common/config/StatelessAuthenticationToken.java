package com.viathink.api.common.config;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class StatelessAuthenticationToken implements AuthenticationToken {
    private static final long serialVersionUID= 1L;
    private String token;
    StatelessAuthenticationToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
