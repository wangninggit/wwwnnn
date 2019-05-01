package com.viathink.core.auth.bean;

import lombok.Data;

@Data
public class AuthTokenEntity {
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private Long updateTime;
    private Long createTime;
}
