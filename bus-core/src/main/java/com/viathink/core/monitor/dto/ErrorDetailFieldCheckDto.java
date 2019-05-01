package com.viathink.core.monitor.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorDetailFieldCheckDto extends ErrorDetailBaseDto{
    private List<String> errorList;
}
