package com.service;

import com.domain.UserRole;

import java.util.List;

public interface UserRoleServiceInternal {

    List<UserRole> findByUserID(String userId);
}
