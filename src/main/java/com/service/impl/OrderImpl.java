package com.service.impl;

import com.domain.Order;
import com.domain.Product;
import com.domain.ProductOrder;
import com.exception.BusinessException;
import com.mapper.OrderMapper;
import com.mapper.ProductMapper;
import com.model.dto.OrderDTO;
import com.model.dto.ProductDTO;
import com.repository.OrderProductRepository;
import com.repository.OrderRepository;
import com.repository.ProductRepository;
import com.service.IOrderService;
import com.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderImpl extends BaseService<OrderRepository, OrderDTO, Order, OrderMapper> implements IOrderService {
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public OrderDTO create(OrderDTO dto) {
        Order order = new Order();
        order.setName(dto.getName());
        order.setTotalPrice((double) 0);
        List<ProductDTO> listProduct = new ArrayList<>();
        OrderDTO orderDTO = new OrderDTO();
        for (ProductDTO productDTO : dto.getProductDTOList()) {
            Product product = productRepository.findEntityById(productDTO.getId()).orElse(null);
            if (ObjectUtils.isEmpty(product))
                throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, ErrorCode.PRODUCT_NOT_FOUND.getMessage());
            ProductDTO dtoProduct = productMapper.toDto(product);
            if (product.getQuantity() > productDTO.getQuantity()) {
                dtoProduct.setQuantity(productDTO.getQuantity());
                listProduct.add(dtoProduct);
            } else {
                throw new BusinessException(ErrorCode.PRODUCT_NOT_ENOUGH, ErrorCode.PRODUCT_NOT_ENOUGH.getMessage());
            }
            // add productDto vào trong list ProductDTO
//            listProduct.add(dtoProduct);
        }
        repository.insert(order);
        Double total = Double.valueOf(0);
        for (ProductDTO productDTO : listProduct) {
            ProductOrder productOrder = new ProductOrder();
            Product product = productRepository.findEntityById(productDTO.getId()).orElse(null);
            productOrder.setOrder(order);
            productOrder.setProduct(productMapper.toEntity(productDTO));
            productOrder.setQty(productDTO.getQuantity());
            total += productDTO.getPrice() * productDTO.getQuantity();
            product.setQuantity(product.getQuantity() - productOrder.getQty());
            orderProductRepository.insert(productOrder);
            productRepository.update(product);
        }
        order.setTotalPrice(total);
        repository.update(order);
        orderDTO.setId(order.getId());
        orderDTO.setName(order.getName());
        orderDTO.setProductDTOList(listProduct);
        orderDTO.setTotalPrice(total);
        return orderDTO;
    }

    @Override
    public OrderDTO update(OrderDTO dto) {
        return null;
    }

    @Override
    public Page<OrderDTO> getAll(Map<String, String> params, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        String name = !ObjectUtils.isEmpty(params.get("name")) ? params.get("name").trim().toLowerCase(Locale.ROOT) : null;
        Page<OrderDTO> orderList = repository.findAllOrder(name, pageable);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderDTO order : orderList) {
            OrderDTO orderDTO = getDetailsOrder(order.getId());
            orderDTOList.add(orderDTO);
        }
        Page<OrderDTO> pageOrder = new PageImpl<>(orderDTOList);
        return pageOrder;
    }


    public OrderDTO getDetailsOrder(Integer id) {
        Order order = repository.findEntityById(id).orElse(null);
        if (ObjectUtils.isEmpty(order))
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        List<ProductOrder> productOrderList = orderProductRepository.findByOrderId(id);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setName(order.getName());
        orderDTO.setTotalPrice(order.getTotalPrice());
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (ProductOrder productOrder : productOrderList) {
            productDTOList.add(productMapper.toDto(productOrder.getProduct()));
        }
        orderDTO.setProductDTOList(productDTOList);
        return orderDTO;
    }

    @Override
    public Optional<OrderDTO> getDetails(Integer id) {
        OrderDTO orderDTO = getDetailsOrder(id);
        return Optional.of(orderDTO);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Order order = repository.findEntityById(id).orElse(null);
        if (ObjectUtils.isEmpty(order))
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        List<ProductOrder> productOrderList = orderProductRepository.findByOrderId(id);
        for (ProductOrder productOrder : productOrderList) {
            orderProductRepository.softDelete(productOrder.getId(), new Date());
        }
        repository.softDelete(id, new Date());
        for (ProductOrder productOrder : productOrderList) {
            Product product = productRepository.findEntityById(productOrder.getProduct().getId()).orElse(null);
            product.setQuantity(product.getQuantity() + productOrder.getQty());
            productRepository.update(product);
        }
    }

    @Override
    public void delete(List<Integer> ids) {

    }

    @Override
    protected String getEntityName() {
        return null;
    }

//    @Override
//    public void export(Integer id) throws DocumentException, IOException {
//
//    }
}
