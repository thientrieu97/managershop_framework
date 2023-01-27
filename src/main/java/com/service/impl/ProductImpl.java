package com.service.impl;

import com.domain.Product;
import com.exception.BusinessException;
import com.mapper.ProductMapper;
import com.model.dto.ProductDTO;
import com.repository.ProductRepository;
import com.service.IProductService;
import com.util.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ProductImpl extends BaseService<ProductRepository, ProductDTO, Product, ProductMapper>implements IProductService {
    @Override
    public ProductDTO create(ProductDTO dto) {
        Product product = mapper.toEntity(dto);
        repository.insert(product);
        return mapper.toDto(product);
    }

    @Override
    public ProductDTO update(ProductDTO dto) {
        Product product = repository.findEntityById(dto.getId()).orElse(null);
        if(ObjectUtils.isEmpty(product))
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND,ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        mapper.updateFromDTO(dto,product);
        repository.update(product);
        return mapper.toDto(product);
    }

    @Override
    public Page<ProductDTO> getAll(Map<String, String> params, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        String name = !ObjectUtils.isEmpty(params.get("name")) ? params.get("name").trim().toLowerCase(Locale.ROOT) : null;
        return repository.getAllProduct(name,pageable);
    }
    @Override
    public Optional<ProductDTO> getDetails(Integer id) {
        Product product = repository.findEntityById(id).orElse(null);
        if(ObjectUtils.isEmpty(product))
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND,ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        return Optional.of(mapper.toDto(product));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Product product = repository.findEntityById(id).orElse(null);
        if(ObjectUtils.isEmpty(product))
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND,ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        repository.softDelete(id,new Date());
    }

    @Transactional
    @Override
    public void delete(List<Integer> ids) {
        for(int id : ids){
            delete(id);
        }
    }

    @Override
    protected String getEntityName() {
        return null;
    }
}
