package com.sv.orderdetailservice.service;

import com.sv.orderdetailservice.client.ProductClientRest;
import com.sv.orderdetailservice.domain.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFeignService implements ProductService {

    private final ProductClientRest productClient;

    public ProductFeignService(ProductClientRest productClient) {
        this.productClient = productClient;
    }


    @Override
    public List<ProductDTO> findAll() {
        return null;
    }

    @Override
    public ProductDTO getById(Integer id) {
        return null;
    }
}
