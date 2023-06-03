package com.sv.service.payment.dto;

import com.sv.service.payment.domain.PaymentStatus;
import lombok.Builder;

@Builder
public record PaymentDTO(Long id, Long orderId, PaymentStatus paymentStatus, Double total) {
}
