package com.sv.orderservice.service;

import com.sv.orderservice.client.OrderDetailClientRest;
import com.sv.orderservice.dto.OrderDetailDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailFeignService implements OrderDetailService{

    private final OrderDetailClientRest orderDetailClientRest;

    public OrderDetailFeignService(OrderDetailClientRest orderDetailClientRest) {
        this.orderDetailClientRest = orderDetailClientRest;
    }

    @Override
    public List<OrderDetailDTO> findAll() {
        return orderDetailClientRest.findAll();
    }
}
