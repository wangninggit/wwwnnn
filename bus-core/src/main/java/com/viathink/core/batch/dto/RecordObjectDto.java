package com.viathink.core.batch.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecordObjectDto {
    List list = new ArrayList();
    Object sum = null;
}
