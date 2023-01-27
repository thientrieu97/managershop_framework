package com.model.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO extends BaseDTO {
    String name;
    List<ProductDTO> productDTOList;
    Double totalPrice;

    public OrderDTO(Integer id, String name, Double totalPrice) {
        this.id = id;
        this.name = name;
        this.totalPrice = totalPrice;
    }
}
