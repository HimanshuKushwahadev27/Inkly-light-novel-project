package com.emi.payment_service.RequestDto;

import java.util.UUID;

import com.emi.payment_service.enums.PaymentMethodType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Request DTO to initiate payment for a specific order")
public record RequestPaymentDto(

        @NotNull(message = "Order ID is required")
        @Schema(
            description = "Unique identifier of the order for which payment is being initiated",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        )
        UUID orderId,
        
        @Schema(
                description = "ISO currency code",
                example = "INR",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}$")
        String currency,
        
        @NotBlank
        @Schema(
            description = "Stripe payment method ID generated on frontend",
            example = "pm_1NqgR7A9abcd123"
        )
        String paymentMethodId,

        @NotBlank
        @Schema(
            description = "type of method used for payment",
            example = " CARD , UPI, NET BANKING, WALLET"
        )
        PaymentMethodType type 

) {}