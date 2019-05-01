package com.viathink.core.collection.service;

import java.util.List;
import java.util.Map;

public interface CollectionService {
    List<Map<String,Object>> getCollectionData();
    void updateRecordId(long id);
}
