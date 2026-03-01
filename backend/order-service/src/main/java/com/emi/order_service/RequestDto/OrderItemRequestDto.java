package com.emi.order_service.RequestDto;

import java.math.BigDecimal;
import java.util.UUID;

import com.emi.order_service.enums.OrderType;

public record OrderItemRequestDto(
		UUID bookid,
		BigDecimal price,
		UUID chapterId,
		OrderType type
		) {

}
