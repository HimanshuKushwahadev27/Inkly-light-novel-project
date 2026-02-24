package com.emi.Authoring_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import com.emi.Authoring_service.Repository.DraftBookRepo;
import com.emi.Authoring_service.ResponseDtos.ResponseDraftBookDto;
import com.emi.Authoring_service.entity.AuthorDraftBook;
import com.emi.Authoring_service.enums.BookLifeCycleStatus;
import com.emi.Authoring_service.enums.BookVisibilityStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthoringServiceBookApplicationTests {

	@Autowired
	private DraftBookRepo bookRepo;
	@LocalServerPort
	private Integer port ;
	private UUID authorId=UUID.randomUUID() ;
 	private UUID keycloakId = UUID.randomUUID(); 
 	private UUID catalogBookId = UUID.randomUUID();
 	private UUID bookId;
	
	@BeforeAll
	static void setUpZone() {
	    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	@BeforeEach
	void setUp() {
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
	}
	
	@BeforeEach
	void globalSetUp() {
		bookRepo.deleteAll();
		
		AuthorDraftBook draftBook = new AuthorDraftBook();
		draftBook.setCatalogBookId(catalogBookId);
		draftBook.setAuthorId(authorId);
		draftBook.setTitle("Spring Boot Mastery");
		draftBook.setDescription("This is a complete guide to Spring Boot development.");
		draftBook.setIsbn("9780132350884");
		draftBook.setPrice(new BigDecimal(499.99));
		draftBook.setStatusLifecycle(BookLifeCycleStatus.DRAFT);
		draftBook.setStatusVisible(BookVisibilityStatus.PUBLIC);
		draftBook.setCreatedAt(Instant.now());
		draftBook.setUpdatedAt(Instant.now());
		draftBook.setFreePreview(true);
		
		draftBook = bookRepo.save(draftBook);
		bookId= draftBook.getId();
	}
	
	@Test
	void createBook_Success() {
		   ResponseDraftBookDto response =
			        RestAssured
			            .given()
			                .contentType(ContentType.JSON)
			                .body(TestFactory.validrequest(UUID.randomUUID()))
			            .when()
			                .post("/api/authoring/bookDrafts/create")
			            .then()
			                .statusCode(200)
			                .extract()
			                .as(ResponseDraftBookDto.class);

		    assertEquals("Spring Boot Mastery", response.title());
		
	}
	
	

}
