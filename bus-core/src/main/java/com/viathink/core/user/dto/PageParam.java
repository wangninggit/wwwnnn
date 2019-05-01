package com.viathink.core.user.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import lombok.Data;

@Data
public class PageParam {

    private Integer pageSize = 10;
    private Integer pageNum = 1;
}
