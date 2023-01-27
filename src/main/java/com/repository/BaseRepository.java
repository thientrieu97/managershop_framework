package com.repository;

import com.domain.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<E extends BaseEntity, I> {

    @Query("FROM #{#entityName} E " +
        "   WHERE E.id = :id " +
        "   AND E.deletedAt IS NULL ")
    Optional<E> findEntityById(@Param("id") I id);

    @Query("UPDATE #{#entityName} E " +
        "   SET E.deletedAt = :deletedAt " +
        "   WHERE E.id = :id")
    @Modifying
    int softDelete(@Param("id") I id, @Param("deletedAt") Date deletedAt);

    @Query("UPDATE #{#entityName} E " +
        "   SET E.deletedAt = :deletedAt " +
        "   WHERE E.id in :ids")
    @Modifying
    int softDelete(@Param("ids") Iterable<I> ids, @Param("deletedAt") Date deletedAt);

    @Query("FROM #{#entityName} E " +
        "   WHERE E.deletedAt IS NULL")
    List<E> getAll();

    @Query("FROM #{#entityName} E " +
        "   WHERE E.deletedAt IS NULL")
    Page<E> getAll(Pageable pageable);

    @Query("FROM #{#entityName} E " +
        "   WHERE E.deletedAt IS NOT NULL")
    List<E> getAllDeleted();

    @Query("FROM #{#entityName} E " +
        "   WHERE E.id in :ids " +
        "   AND E.deletedAt IS NULL ")
    List<E> getAllByIdIn(@Param("ids") Iterable<I> ids);
}
