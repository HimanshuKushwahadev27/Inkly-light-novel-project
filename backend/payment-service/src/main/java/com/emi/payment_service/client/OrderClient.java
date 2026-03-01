package com.emi.payment_service.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.emi.payment_service.ResponseDto.ResponseOrderDto;


@FeignClient(value = "order-service", url = "http://order-service:8080")
public interface OrderClient {

	
	@GetMapping("/api/orders/validation/{orderId}")
	public ResponseOrderDto orderVaidation(
			@PathVariable UUID orderId
			);
	
	
}
