package com.repository;

import com.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>, InsertUpdateRepository<Role> {

    List<Role> findByNameIn(Set<String> roleName);
}
