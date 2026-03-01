package com.emi.payment_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.payment_service.entity.Payments;

public interface PaymentRepo extends JpaRepository<Payments, UUID> {

	Payments findByGatewayTransactionId(String transactionId);

}
