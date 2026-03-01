package com.emi.payment_service.gateway;

import com.emi.payment_service.RequestDto.GatewayPaymentRequest;
import com.emi.payment_service.ResponseDto.GatewayResponse;

public interface PaymentGateway {

    GatewayResponse charge(GatewayPaymentRequest request);

}
