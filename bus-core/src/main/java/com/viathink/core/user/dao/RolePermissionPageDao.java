package com.viathink.core.user.dao;

import java.util.List;
import java.util.Map;

public interface RolePermissionPageDao {
    Map<String, Map<String,Object>> getAllPermissions();
    List<Long> getUpdatePermissionIds(Map<String, Map<String,Object>> map);
}
