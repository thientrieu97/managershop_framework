package com.repository;

import java.util.List;

public interface InsertUpdateRepository<T> {
    <S extends T> S insert(S entity);

    <S extends T> S update(S entity);

    <S extends T> List<S> insertAll(List<S> entities);

    <S extends T> List<S> updateAll(List<S> entities);
}
