package com.emi.Author_Service.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating or updating an author profile")
public record RequestAuthorDto(

    @NotBlank(message = "Pen name is required")
    @Size(min = 3, max = 100, message = "Pen name must be between 3 and 100 characters")
    @Schema(
        description = "Public display name of the author",
        example = "Himanshu Kushwaha"
    )
    String penName,

    @NotBlank(message = "Bio is required")
    @Size(min = 10, max = 2000, message = "Bio must be between 10 and 2000 characters")
    @Schema(
        description = "Short biography describing the author",
        example = "Backend engineer and microservices enthusiast writing about distributed systems."
    )
    String bio


) {}