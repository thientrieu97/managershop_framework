package com.model.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO extends BaseDTO {
    String name;
    Integer price;
    Integer quantity;
    Double weight;
    String address;

    public ProductDTO(Integer id,String name, Integer price, Integer quantity, Double weight, String address) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.address = address;
    }
}
