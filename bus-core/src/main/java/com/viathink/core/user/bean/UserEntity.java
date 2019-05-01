package com.viathink.core.user.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class UserEntity {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String phoneNumber;
    private String email;
    private String nickname;
    @JSONField(serialize = false)
    private String password;
    @JSONField(serialize = false)
    private String salt;
    private Boolean deleted;
    private Boolean activate;
    private Long updateTime;
    private Long createTime;
}
