package com.emi.order_service.ResponseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.emi.order_service.enums.OrderStatus;

public record ResponseOrderDto(
		UUID userId,
        UUID orderId,
        BigDecimal totalAmount,
        OrderStatus status,
        Instant createdAt,
        List<ResponseOrderItemDto> items
		) {

}
