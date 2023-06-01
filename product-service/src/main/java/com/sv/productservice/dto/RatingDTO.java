package com.sv.productservice.dto;

import lombok.*;

@Builder
public record RatingDTO(double rate, int count) {
}
