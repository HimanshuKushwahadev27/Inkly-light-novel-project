package com.emi.User_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emi.User_service.requestDto.RequestUserLibraryDto;
import com.emi.User_service.responseDto.ResponseUserLibraryDto;
import com.emi.User_service.service.UserLibraryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/userlibrary")
public class UserLibraryController {

	private final UserLibraryService userLibraryService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseUserLibraryDto> create(
			 @RequestHeader("X-User-Id") String keycloakId,
			@RequestBody @Valid RequestUserLibraryDto request) {
		return ResponseEntity.ok(userLibraryService.create(request, UUID.fromString(keycloakId)));
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<ResponseUserLibraryDto>> getAll(@RequestHeader("X-User-Id") String keycloakId){
		return ResponseEntity.ok(userLibraryService.getAll(UUID.fromString(keycloakId)));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id, @RequestHeader("X-User-Id") String keycloakId) {
		return ResponseEntity.ok(userLibraryService.delete(id, UUID.fromString(keycloakId)));
	}
}
