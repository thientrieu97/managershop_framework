package com.service;

import com.domain.Order;
import com.domain.Product;
import com.mapper.OrderMapper;
import com.mapper.ProductMapper;
import com.model.dto.OrderDTO;
import com.model.dto.ProductDTO;
import com.repository.OrderRepository;
import com.repository.ProductRepository;
import org.dom4j.DocumentException;

import java.io.IOException;

public interface IOrderService extends IBaseService<OrderRepository, OrderDTO, Order, OrderMapper> {

}
