package com.emi.order_service.ResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

import com.emi.order_service.enums.OrderType;

public record ResponseOrderItemDto(
        OrderType itemType,
        UUID bookId,
        UUID chapterId,
        BigDecimal price
) {}