package com.emi.order_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.order_service.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, UUID> {

	List<OrderItem> findAllByOrderId(UUID id);

}
