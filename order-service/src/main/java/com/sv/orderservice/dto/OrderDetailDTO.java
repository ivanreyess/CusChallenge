package com.sv.orderservice.dto;

public record OrderDetailDTO(Long id, Long orderId, Integer quantity, Double price, Integer productId) {
}
