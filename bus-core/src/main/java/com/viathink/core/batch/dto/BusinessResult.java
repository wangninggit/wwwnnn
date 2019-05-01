package com.viathink.core.batch.dto;

import lombok.Data;

import java.util.List;

@Data
public class BusinessResult {
    private BusinessDetailResult sum;
    private List<BusinessDetailResult> list;
}
