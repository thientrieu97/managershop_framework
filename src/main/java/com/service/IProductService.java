package com.service;

import com.domain.Product;
import com.mapper.ProductMapper;
import com.model.dto.ProductDTO;
import com.repository.ProductRepository;

public interface IProductService extends IBaseService<ProductRepository, ProductDTO, Product, ProductMapper>{
}
