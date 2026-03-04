package com.emi.payment_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.payment_service.entity.IdempotencyRecord;

public interface IdempotencyRepo extends JpaRepository<IdempotencyRecord, UUID> {


	 Optional<IdempotencyRecord> findByUserKeycloakIdAndIdempotencyKey(UUID userKeycloakId, UUID idempotencyId);

	

}
