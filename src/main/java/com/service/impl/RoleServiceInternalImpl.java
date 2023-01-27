package com.service.impl;

import com.domain.Role;
import com.repository.RoleRepository;
import com.service.RoleServiceInternal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceInternalImpl implements RoleServiceInternal {

    private final RoleRepository roleRepository;

    @Override
    public boolean checkRole(Set<String> roleName) {
        List<Role> userRoleList = roleRepository.findByNameIn(roleName);
        return !CollectionUtils.isEmpty(userRoleList);
    }
}
