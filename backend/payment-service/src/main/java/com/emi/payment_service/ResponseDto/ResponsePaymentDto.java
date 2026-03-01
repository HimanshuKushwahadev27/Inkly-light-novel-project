package com.emi.payment_service.ResponseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.emi.events.PaymentStatus;


public record ResponsePaymentDto(
        UUID paymentId,
        UUID orderId,
        BigDecimal amount,
        PaymentStatus status,
        String gatewayTransactionId,
        Instant createdAt) {

}
