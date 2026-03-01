package com.emi.payment_service.RequestDto;

import java.util.UUID;

public record RequestPaymentDto(
	UUID orderID
		) {

}
