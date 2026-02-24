package com.emi.User_service.mapper;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.emi.User_service.entity.Review;
import com.emi.User_service.requestDto.RequestReviewDto;
import com.emi.User_service.responseDto.ResponseReviewDto;

@Component
public class ReviewMapper {

	public Review toEntity(RequestReviewDto request, UUID keycloakId, UUID userId) {
		
		Review review = new Review();
		review.setBookId(request.bookId());
		review.setComment(request.comment());
		review.setKeycloakId(keycloakId);
		review.setCreatedAt(Instant.now());
		review.setRating(request.rating());
		review.setUserId(userId);
		
		return review;
	}

	public ResponseReviewDto toDto(Review review) {
	 return new	ResponseReviewDto(
			 review.getId(),
			 review.getBookId(),
			 review.getUserId(),
			 review.getRating(),
			 review.getComment(),
			 review.getCreatedAt()
			 );
	}

}
