package com.sv.orderservice.dto;

import lombok.Builder;

@Builder
public record OrderDTO(Long id, String name, String lastName, Double total, String city, String country, String email, String shippingAddress, long createdDate, long modifiedDate) {
}
