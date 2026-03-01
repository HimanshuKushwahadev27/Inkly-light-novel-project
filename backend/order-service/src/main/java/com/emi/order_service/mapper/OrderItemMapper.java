package com.emi.order_service.mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.order_service.RequestDto.OrderItemRequestDto;
import com.emi.order_service.entity.OrderItem;

@Component
public class OrderItemMapper {

	public List<OrderItem> getEntities(List<OrderItemRequestDto> items, UUID orderId) {
		return items
				.stream()
				.map(t -> 
				new OrderItem(
						orderId,
						t.price(),
						t.type(), 
						t.bookid(),
						t.chapterId(),
						Instant.now(),
						Instant.now())
				)
				.toList();
	}

	public BigDecimal getTotalPrice(List<OrderItem> items) {
		return items.stream()
				.map(OrderItem::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	

}
