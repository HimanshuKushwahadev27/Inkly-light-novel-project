package com.emi.order_service.service;

import java.util.List;
import java.util.UUID;

import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.ResponseDto.OrderHistoryDto;
import com.emi.order_service.ResponseDto.ResponseOrderDto;
import com.emi.order_service.ResponseDto.ResponseOrderToPaymentDto;

public interface OrderService {
	
	public ResponseOrderDto createOrder(
			RequestOrderDto request,
			UUID idempId,
			UUID keycloakId,
			String  email, 
			String firstName,
			String lastName);
	public ResponseOrderDto getOrder(
			UUID orderId,
			UUID keycloakId);
	public List<ResponseOrderDto> getMyOrders(UUID userKeycloakId);
	public void cancelOrder(
			UUID orderId,
			UUID userKeycloakId);
	public void markPaid(UUID orderId, UUID paymentId);
	public void markFailed(UUID orderId, UUID paymentId);
	public List<OrderHistoryDto> getHistory(UUID orderId);
	public List<ResponseOrderDto> getAllOrders();
	public ResponseOrderToPaymentDto orderValidation(UUID orderId);
}
