package com.emi.payment_service.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.emi.payment_service.exceptions.PaymentNotFoundException;
import com.emi.payment_service.exceptions.UnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseEntity<?> handlePaymentFound(PaymentNotFoundException ex){
		return ResponseEntity
				.status(404)
				.body(ex.getMessage());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> handleUnauthorized(UnauthorizedException ex){
		return ResponseEntity
				.status(401)
				.body(ex.getMessage());
	}
}
