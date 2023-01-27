package com.repository;

import com.domain.User;
import com.model.bo.StatusCommon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, InsertUpdateRepository<User>, BaseRepository<User, String> {


    Optional<User> findByEmailAndStatusNot(String email, StatusCommon statusCommon);

    Optional<User> findByUserNameAndStatusNot(String userName, StatusCommon statusCommon);


    List<User> findByStatus(StatusCommon statusCommon);

    @Query(value = "select * from users where lower(full_name) like ?1 and status = 'WAITING' and delete_at is null ", nativeQuery = true)
    List<User> findByFullNameOrUserName(String nameLike);


    List<User> findByIdInAndStatusNot(Set<String> userIds, StatusCommon statusCommon);

    @Query(value = "select * from users where lower(full_name) like ?1 and status = 'ACTIVE' and delete_at is null ", nativeQuery = true)
    List<User> findByIdInAndStatusNotByName(String name, Set<String> userIds, StatusCommon statusCommon);


    @Query("SELECT u.id " +
            "FROM  User u " +
            "WHERE u.deletedAt IS NULL ")
    List<String> getAllUserId();
}
