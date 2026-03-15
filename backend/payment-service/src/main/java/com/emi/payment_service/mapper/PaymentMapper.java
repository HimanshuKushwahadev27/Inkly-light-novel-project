package com.emi.payment_service.mapper;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.events.payment.PaymentStatus;
import com.emi.payment_service.ResponseDto.ResponseOrderDto;
import com.emi.payment_service.ResponseDto.ResponsePaymentDto;
import com.emi.payment_service.entity.Payments;
import com.emi.payment_service.enums.PaymentMethodType;

@Component
public class PaymentMapper {

	public Payments toEntity(ResponseOrderDto request, UUID keycloakId, String currency, PaymentMethodType type  ) {
		Payments payment = new Payments();
		
		payment.setAmount(request.totalAmount());
		payment.setCreatedAt(Instant.now());
		payment.setOrderId(request.orderId());
		payment.setStatus(PaymentStatus.INITIATED);
		payment.setUserKeycloakId(keycloakId);
		payment.setUpdatedAt(Instant.now());
		payment.setCurrency(currency);
		payment.setPaymentMethodType(type);
		return payment;
	}

	public ResponsePaymentDto toDto(Payments payment, String secret) {
		return new ResponsePaymentDto(
				payment.getId(),
				payment.getOrderId(),
				payment.getAmount(),
				payment.getStatus(),
				payment.getGatewayTransactionId().toString(),
				payment.getCreatedAt(),
				payment.getCurrency(),
				payment.getPaymentMethodType(),
				secret
		)
				;
	}



}
