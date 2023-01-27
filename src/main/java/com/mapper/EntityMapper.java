package com.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface EntityMapper  <D,E>{

    E toEntity(D dto);
    D toDto(E entity);
    List<E> toEntity(List<D> dtoList);
    List<D> toDto(List<E> entityList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(D dto, @MappingTarget E entity);
}