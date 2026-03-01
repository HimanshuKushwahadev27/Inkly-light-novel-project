package com.emi.payment_service.service;

import java.util.UUID;

import com.emi.payment_service.RequestDto.RequestPaymentDto;
import com.emi.payment_service.ResponseDto.ResponsePaymentDto;

public interface PaymentService {

	public ResponsePaymentDto create(
			RequestPaymentDto request,
			UUID idempotencyId,
			UUID keycloakId);
		
	
	public ResponsePaymentDto getPayment(UUID paymentId, UUID keycloakId);

	public void webhook(String payload, String sigHeader);
	
	
}
