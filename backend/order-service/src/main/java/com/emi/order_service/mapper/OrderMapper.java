package com.emi.order_service.mapper;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.order_service.ResponseDto.ResponseOrderDto;
import com.emi.order_service.ResponseDto.ResponseOrderItemDto;
import com.emi.order_service.ResponseDto.ResponseOrderToPaymentDto;
import com.emi.order_service.entity.Order;
import com.emi.order_service.entity.OrderItem;
import com.emi.order_service.enums.OrderStatus;

@Component
public class OrderMapper {

	public Order getEntity(UUID keycloakId, String email, String firstName, String lastName) {
		Order order = new Order();
		order.setCreatedAt(Instant.now());
		order.setStatus(OrderStatus.CREATED);
		order.setUserKeycloakId(keycloakId);
		order.setUpdatedAt(Instant.now());
		order.setFirstName(firstName);
		order.setLastName(lastName);
		order.setUserEmail(email);
		return order;
	}


	public ResponseOrderDto getResponse(Order order, List<OrderItem> items) {
        List<ResponseOrderItemDto> itemsDto = items
        		.stream()
        		.map(
        			t -> new ResponseOrderItemDto(t.getItemType(), t.getBookId(), t.getChapterId(), t.getAmount()))
        		.toList();

        return new ResponseOrderDto(
        		order.getUserKeycloakId(),
        		order.getId(),
        		order.getAmount(),
        		order.getStatus(),
        		order.getCreatedAt(),
        		itemsDto)
        
        		;
	}


	public ResponseOrderToPaymentDto getResponseToPayment(Order order) {
		return new ResponseOrderToPaymentDto(
				order.getUserKeycloakId(),
				order.getId(),
				order.getAmount(),
				order.getStatus(),
				order.getCreatedAt()
				)
				;
	}

}
