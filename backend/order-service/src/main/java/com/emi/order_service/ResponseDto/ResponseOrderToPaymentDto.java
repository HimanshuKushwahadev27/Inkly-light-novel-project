package com.emi.order_service.ResponseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.emi.order_service.enums.OrderStatus;

public record ResponseOrderToPaymentDto(
		UUID userId,
        UUID orderId,
        BigDecimal totalAmount,
        OrderStatus status,
        Instant createdAt
		) {

}
