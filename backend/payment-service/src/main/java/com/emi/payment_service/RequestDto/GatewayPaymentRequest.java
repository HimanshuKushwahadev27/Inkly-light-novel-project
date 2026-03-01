package com.emi.payment_service.RequestDto;

import java.math.BigDecimal;

public record GatewayPaymentRequest(
		
        BigDecimal amount,
        String orderId,
        String idempotencyKey
		) {

}
