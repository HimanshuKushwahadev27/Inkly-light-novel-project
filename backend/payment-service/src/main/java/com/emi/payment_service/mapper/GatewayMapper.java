package com.emi.payment_service.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.payment_service.RequestDto.GatewayPaymentRequest;
import com.emi.payment_service.entity.Payments;

@Component
public class GatewayMapper {

	public  GatewayPaymentRequest getRequest(Payments payment, UUID idempotencyId, String currency, String idPayment) {
		return new GatewayPaymentRequest(
				payment.getAmount(),
				payment.getOrderId().toString(),
				idempotencyId.toString(),
				currency,
				idPayment
				);
	}

}
