package com.emi.order_service.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.emi.order_service.exceptions.OrderExistsException;
import com.emi.order_service.exceptions.UnauthorizedAccessException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(OrderExistsException.class)
	public ResponseEntity<?> handleBookExistsFound(OrderExistsException ex){
		return ResponseEntity
				.status(404)
				.body(ex.getMessage());
	}
	
	
	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<?> handleBookExistsFound(UnauthorizedAccessException ex){
		return ResponseEntity
				.status(404)
				.body(ex.getMessage());
	}
	
	
}
