package com.sv.orderservice.dto;

import com.sv.orderservice.domain.PaymentStatus;
import lombok.Builder;

@Builder
public record PaymentDTO(Long orderId, PaymentStatus status, Double total) {
}
