package com.mapper;


import com.domain.Product;
import com.model.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
//Map giua DTO va ENtity. va thuoc thang Spring
public interface ProductMapper extends EntityMapper <ProductDTO, Product>{
}
