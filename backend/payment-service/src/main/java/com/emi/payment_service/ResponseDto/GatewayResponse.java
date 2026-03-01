package com.emi.payment_service.ResponseDto;

import java.math.BigDecimal;

import com.emi.payment_service.enums.GatewayPaymentStatus;

public record GatewayResponse(
        String transactionId,
        GatewayPaymentStatus status,
        BigDecimal amount
		) {

}
