package com.emi.payment_service.ResponseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.emi.payment_service.enums.OrderStatus;



public record ResponseOrderDto(
		UUID userId,
        UUID orderId,
        BigDecimal totalAmount,
        OrderStatus status,
        Instant createdAt
        ) {

}
