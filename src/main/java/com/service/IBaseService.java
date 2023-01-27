package com.service;


import com.domain.BaseEntity;
import com.mapper.EntityMapper;
import com.model.dto.BaseDTO;
import com.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IBaseService<R extends BaseRepository, D extends BaseDTO, E extends BaseEntity, M extends EntityMapper> {

    public abstract D create(D dto);

    public abstract D update(D dto);

    public abstract Page<D> getAll(Map<String, String> params, Integer page, Integer size);

    public abstract Optional<D> getDetails(Integer id);

    public abstract void delete(Integer id);

    public abstract void delete(List<Integer> ids);
}
