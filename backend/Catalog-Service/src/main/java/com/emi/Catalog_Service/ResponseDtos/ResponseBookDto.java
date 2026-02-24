package com.emi.Catalog_Service.ResponseDtos;

import java.math.BigDecimal;
import java.util.UUID;

import com.emi.Catalog_Service.enums.BookLifeCycleStatus;
import com.emi.Catalog_Service.enums.BookVisibilityStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Book representation used in Catalog responses during creation or update operations")
public record ResponseBookDto (
		
	    @NotBlank
	    @Schema(
	        description = "Id of the book",
	        example = "550e8400-e29b-41d4-a716-446655440000"
	        		)
		UUID bookId,
		
	    @NotNull
	    @Positive
	    @Schema(
	        description = "Price of the book",
	        example = "499.99"
	    )
		BigDecimal price,
		
	    @NotBlank
	    @Size(max = 1000)
	    @Schema(
	        description = "Detailed description of the book",
	        example = "A deep dive into Spring Boot internals and architecture"
	    )
		String description,
		
	    @NotBlank
	    @Schema(
	        description = "Publication status of the book",
	        example = "ONGOING, DRAFT, COMPLETED"
	    )
		BookLifeCycleStatus lifeCycleStatus,
		
	    @NotBlank
	    @Schema(
	        description = "Publication status of the book",
	        example = "PRIVATE, PUBLIC"
	    )
		BookVisibilityStatus visibilityStatus,
		
	    @NotNull
	    @Min(0)
	    @Schema(
	        description = "Total number of published chapters",
	        example = "12"
	    )
		Integer totalChapters, 
		
		 @NotBlank
	    @Schema(
	        description = "Operation result message",
	        example = "Book created successfully"
	    )
		String message
		){
}
