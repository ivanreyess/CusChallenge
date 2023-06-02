package com.sv.orderdetailservice.dto;

import lombok.Builder;

@Builder
public record OrderDetailDTO(long id, Integer orderId, Integer quantity, Double price, Integer productId, Long createdDate, Long modifiedDate) {
}
