package com.emi.payment_service.ResponseDto;

public record StripeRefundResponseDto(
        String id,
        String status,
        String payment_intent,
        Long amount
		) {

}
