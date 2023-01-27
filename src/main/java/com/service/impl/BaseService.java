package com.service.impl;
import com.domain.BaseEntity;
import com.exception.ResourceNotFoundException;
import com.mapper.EntityMapper;
import com.model.dto.BaseDTO;
import com.repository.BaseRepository;
import com.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService <
        R extends BaseRepository,
        D extends BaseDTO,
        E extends BaseEntity,
        M extends EntityMapper>
        implements IBaseService<R, D, E, M> {

    @Autowired
    protected R repository;

    @Autowired
    protected M mapper;

    protected ResourceNotFoundException throwNotFoundException() {
        return new ResourceNotFoundException(getEntityName());
    }

    protected abstract String getEntityName();

    public static Long parseLong(String str) {
        if (str != null) {
            return Long.parseLong(str);
        }
        return null;
    }

    public static Integer parseInteger(String str) {
        if (str != null) {
            return Integer.parseInt(str);
        }
        return null;
    }
}
