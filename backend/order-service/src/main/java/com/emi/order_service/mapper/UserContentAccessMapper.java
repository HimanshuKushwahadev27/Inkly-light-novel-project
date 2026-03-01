package com.emi.order_service.mapper;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.emi.order_service.entity.Order;
import com.emi.order_service.entity.OrderItem;
import com.emi.order_service.entity.UserContentAccess;

@Component
public class UserContentAccessMapper {


	public UserContentAccess getEntity(Order order, OrderItem first) {
		UserContentAccess content = new UserContentAccess();
		
		content.setBookId(first.getBookId());
		content.setAccessType(first.getItemType());
		content.setChapterId(first.getChapterId());
		content.setCreatedAt(Instant.now());
		
		return content;
	}

}
