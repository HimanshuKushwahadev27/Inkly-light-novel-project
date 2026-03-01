package com.emi.payment_service.ResponseDto;

public record StripeResponse(
        String id,
        String status,
        Long amount,
        String currency,
        Long created,
        String client_secret
) {}