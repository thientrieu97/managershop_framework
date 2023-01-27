package com.controller;


import com.model.dto.OrderDTO;
import com.model.dto.ProductDTO;
import com.model.dto.Response;
import com.service.IOrderService;
import com.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/create_order")
    public Response<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return Response.ofSucceeded(orderService.create(orderDTO));
    }

    @GetMapping("/{id}")
    public Optional<OrderDTO> getDetailOder(@PathVariable("id") Integer id) {
        return orderService.getDetails(id);
    }

    @DeleteMapping(value = {"/{id}"})
    public Response<Void> delete(@PathVariable Integer id) {
        orderService.delete(id);
        return Response.ofSucceeded();
    }

    @DeleteMapping(value = {""})
    public Response<Void> deleteMany(@PathVariable List<@Positive Integer> ids) {
        orderService.delete(ids);
        return Response.ofSucceeded();
    }

    @GetMapping("")
    public Response<List<OrderDTO>> getAllOrder(@RequestParam Map<String, String > params ,
                                                @RequestParam(value = "page", defaultValue = "1") Integer page ,
                                                @RequestParam(value = "size", defaultValue = "50") Integer size) {
        Page<OrderDTO> listPage = orderService.getAll(params,page -1 ,size);
        return Response.ofSucceeded(listPage);
    }



}

