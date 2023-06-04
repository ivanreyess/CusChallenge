package com.sv.productservice.service;

import com.sv.productservice.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService{

    private final String resourceUrl = "https://fakestoreapi.com/products";

    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<ProductDTO> getAll() {
        return Optional.ofNullable(restTemplate.getForObject(resourceUrl, ProductDTO[].class))
                                            .map(productDTOS -> Arrays.stream(productDTOS).toList())
                                            .orElse(new ArrayList<>());
    }

    @Override
    public Optional<ProductDTO> getProductById(Integer id) throws InterruptedException {
        if (id == 20) TimeUnit.SECONDS.sleep(5L);
        return Optional.ofNullable(restTemplate.getForObject(resourceUrl + "/{id}", ProductDTO.class, id));
    }
}
