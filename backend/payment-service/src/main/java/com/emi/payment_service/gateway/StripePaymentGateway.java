package com.emi.payment_service.gateway;

import java.math.BigDecimal;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.emi.payment_service.RequestDto.GatewayPaymentRequest;
import com.emi.payment_service.ResponseDto.GatewayResponse;
import com.emi.payment_service.ResponseDto.StripeResponse;
import com.emi.payment_service.enums.GatewayPaymentStatus;

import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StripePaymentGateway implements PaymentGateway {

    private final WebClient stripeWebClient;
    
	@Override
	public GatewayResponse charge(GatewayPaymentRequest request) {
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		
		body.add("amount", request.amount().multiply(BigDecimal.valueOf(100)).toString());
		body.add("payment_method_types[]", "card");
		body.add("currency", request.currency());
		body.add("payment_method", request.paymentMethodId());
    body.add("confirm", "true");

		StripeResponse response = stripeWebClient.post()
				.uri("/v1/payment_intents")
			  .header("Idempotency-Key", request.idempotencyKey().toString())	
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)	
			  .bodyValue(body)
				.retrieve()
				.bodyToMono(StripeResponse.class)
				.block()
				;
		
		return new GatewayResponse(
				response.id(),
		        mapStatus(response.status()),
		        convertAmount(response.amount()),
						response.currency(),
						response.client_secret()
		);
	}

	private BigDecimal convertAmount(Long amount) {
		   if (amount == null) {
		        return BigDecimal.ZERO;
		    }

		    return BigDecimal.valueOf(amount)
		            .divide(BigDecimal.valueOf(100));
	}

	private GatewayPaymentStatus mapStatus(String status) {

    return switch (status) {

        case "succeeded" -> GatewayPaymentStatus.SUCCESS;

        case "requires_payment_method",
             "requires_confirmation",
             "requires_action",
             "processing" -> GatewayPaymentStatus.PENDING;

        case "canceled" -> GatewayPaymentStatus.FAILED;

        default -> GatewayPaymentStatus.FAILED;
    };
}
	
}
