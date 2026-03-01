package com.emi.order_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.order_service.entity.UserContentAccess;
import com.emi.order_service.enums.OrderType;

public interface UserContentAccessRepo extends JpaRepository<UserContentAccess, UUID> {

	boolean existsByUserIdAndBookIdAndAccessType(UUID userId, UUID bookid, OrderType type);

	boolean existsByUserIdAndChapterId(UUID userId, UUID bookid);

}
