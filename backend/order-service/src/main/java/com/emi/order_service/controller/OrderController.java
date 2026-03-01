package com.emi.order_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emi.order_service.RequestDto.RequestOrderDto;
import com.emi.order_service.ResponseDto.OrderHistoryDto;
import com.emi.order_service.ResponseDto.ResponseOrderDto;
import com.emi.order_service.ResponseDto.ResponseOrderToPaymentDto;
import com.emi.order_service.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;
	
	
	@PostMapping("/create")
	public ResponseEntity<ResponseOrderDto> create(
			@RequestBody @Valid RequestOrderDto request, 
			@AuthenticationPrincipal Jwt jwt,
			@RequestHeader("Idempotency-Key") String IdempotencyKey
			) {
		return ResponseEntity.ok(orderService.createOrder(
				request,
				UUID.fromString(jwt.getSubject()),
				UUID.fromString(IdempotencyKey),
				jwt.getClaim("email"), 
				jwt.getClaimAsString("given_name"),
				jwt.getClaimAsString("family_name")));
	}
	
	
	@GetMapping("/get/{orderId}")
	public ResponseEntity<ResponseOrderDto> getMyOrder(
			@PathVariable UUID orderId,
			@AuthenticationPrincipal Jwt jwt
			) {
		return ResponseEntity.ok(orderService.getOrder(orderId, UUID.fromString(jwt.getSubject())));
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<ResponseOrderDto>> getMyOrders(
			@AuthenticationPrincipal Jwt jwt
			) {
		return ResponseEntity.ok(orderService.getMyOrders(UUID.fromString(jwt.getSubject())));
	}
	
	@PatchMapping("/cancel/{orderId}")
	public void cancelMyOrder(
			@PathVariable UUID orderId,
			@RequestHeader("X-User-Id") String keycloakId
			) {
	 orderService.cancelOrder(orderId, UUID.fromString(keycloakId));
	}
	
	
	@GetMapping("/getHistory/{orderId}")
	public ResponseEntity<List<OrderHistoryDto>> getMyOrder(
			@PathVariable UUID orderId
			) {
		return ResponseEntity.ok(orderService.getHistory(orderId));
	}
	
	@GetMapping("/getAllOrders")
	public ResponseEntity<List<ResponseOrderDto>> getAllOrders(
			) {
		return ResponseEntity.ok(orderService.getAllOrders());
	}
	
	
	@GetMapping("/validation/{orderId}")
	public ResponseOrderToPaymentDto orderVaidation(
		
			@PathVariable UUID orderId
			) {
		return orderService.orderValidation(orderId);
	}
	
	
	
	
	
}
