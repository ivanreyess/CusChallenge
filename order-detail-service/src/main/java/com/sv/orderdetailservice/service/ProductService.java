package com.sv.orderdetailservice.service;

import com.sv.orderdetailservice.domain.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll();

    ProductDTO getById(Integer id);
}
