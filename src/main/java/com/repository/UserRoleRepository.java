package com.repository;

import com.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>, InsertUpdateRepository<UserRole> {

    List<UserRole> findByUserId(String userId);

    UserRole findByUserIdAndRoleName(String userId, String roleName);

    @Query(value = "update user_role set role_name = ?1 WHERE user_id = ?2", nativeQuery = true)
    void updateByRoleName(String roleName, String id);

    @Query(value = "select * from user_role where lower(name) like ?1", nativeQuery = true)
    List<UserRole> findByNameLike(String nameLike);

    List<UserRole> findByRoleNameIn(Set<String> roleNames);

    List<UserRole> findByRoleName(String roleNames);
}
