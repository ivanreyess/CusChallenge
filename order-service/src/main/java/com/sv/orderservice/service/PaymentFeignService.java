package com.sv.orderservice.service;

import com.sv.orderservice.client.PaymentClientRest;
import com.sv.orderservice.dto.OrderDTO;
import com.sv.orderservice.dto.PaymentDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentFeignService implements PaymentService {

    private final PaymentClientRest paymentClientRest;

    private final OrderService orderService;

    public PaymentFeignService(PaymentClientRest paymentClientRest, OrderService orderService) {
        this.paymentClientRest = paymentClientRest;
        this.orderService = orderService;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        return paymentClientRest.createPayment(paymentDTO);
    }

    @Override
    public PaymentDTO doPayment(Long orderId) {
        Optional<OrderDTO> orderDTO = orderService.findOne(orderId);
        return orderDTO.map(order -> createPayment(
                PaymentDTO.builder()
                        .orderId(order.id())
                        .total(order.total())
                        .build()
        )).orElse(PaymentDTO.builder().build());
    }
}
