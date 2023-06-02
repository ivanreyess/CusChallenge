package com.sv.orderdetailservice.domain.dto;

import lombok.Builder;

@Builder
public record OrderDetailDTO(long id, Long orderId, Integer quantity, Double price, Integer productId, Long createdDate, Long modifiedDate) {
}