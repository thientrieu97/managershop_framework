package com.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class InsertUpdateRepositoryImpl<T> implements InsertUpdateRepository<T> {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public <S extends T> S insert(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public <S extends T> S update(S entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    @Override
    public <S extends T> List<S> insertAll(List<S> entities) {
        entities.forEach(entityManager::persist);
        return entities;
    }

    @Transactional
    @Override
    public <S extends T> List<S> updateAll(List<S> entities) {
        entities.forEach(entityManager::merge);
        return entities;
    }

}
