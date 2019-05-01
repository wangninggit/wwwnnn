package com.viathink.engine.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.viathink.engine.component.*;

@Configuration
public class ReleaseManagerConfig {
    @Bean
    @Scope("singleton")
    public ReleaseManager releaseManager() {
        return new ReleaseManager();
    }
}
