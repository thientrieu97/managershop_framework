package com.controller;

import com.model.dto.ProductDTO;
import com.model.dto.Response;
import com.model.dto.UserUpdateInfoDTO;
import com.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping("/create_product")
    public Response<ProductDTO> registerAccount(@RequestBody ProductDTO productDTO) {
        return Response.ofSucceeded(productService.create(productDTO));
    }

    @PutMapping("/update")
    public Response<ProductDTO> updateUserInformation(@RequestBody ProductDTO productDTO) {
        return Response.ofSucceeded(productService.update(productDTO));
    }

    @GetMapping("/{id}")
    public Optional<ProductDTO> getDetailProduct(@PathVariable("id") Integer id) {
        return productService.getDetails(id);
    }

    @GetMapping("")
    public Response<List<ProductDTO>> getAllProduct(@RequestParam Map<String, String > params ,
                                                    @RequestParam(value = "page", defaultValue = "1") Integer page ,
                                                    @RequestParam(value = "size", defaultValue = "50") Integer size) {
        Page<ProductDTO> listPage = productService.getAll(params,page -1 ,size);
        return Response.ofSucceeded(listPage);
    }

    @DeleteMapping(value = {"/{id}"})
    public Response<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return Response.ofSucceeded();
    }

    @DeleteMapping(value = {""})
    public Response<Void> deleteMany(@PathVariable List<@Positive Integer> ids) {
        productService.delete(ids);
        return Response.ofSucceeded();
    }
}
