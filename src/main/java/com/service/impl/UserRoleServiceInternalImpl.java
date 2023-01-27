package com.service.impl;

import com.domain.UserRole;
import com.repository.UserRoleRepository;
import com.service.UserRoleServiceInternal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleServiceInternalImpl implements UserRoleServiceInternal {

    private final UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findByUserID(String userId) {
        return userRoleRepository.findByUserId(userId);
    }
}
