package com.emi.Author_Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import com.emi.Author_Service.Repository.AuthorRepo;
import com.emi.Author_Service.entity.Author;
import com.emi.Author_Service.responseDto.ResponseAuthorDto;
import com.emi.Author_Service.responseDto.ResponseAuthorDtoForAdmin;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorServiceApplicationTests {

	@Autowired
	private AuthorRepo authorRepo;
	
	@LocalServerPort
	private Integer port ;
	private UUID authorId ;
 	private UUID keycloakId = UUID.randomUUID(); 
	
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
		authorRepo.deleteAll();
		
		Author author = new Author();
		author.setBio("Backend engineer and microservices enthusiast writing about distributed systems.");
		author.setCreatedAt(Instant.now());
		author.setDeleted(false);
		author.setPenName("Himanshu");
		author.setKeycloakId(keycloakId);
		author.setUpdatedAt(Instant.now());
		author.setVerified(false);
		author.setProfileImageUrl("https://cdn.library.com/authors/profile123.png");
		author = authorRepo.save(author);
		
		authorId= author.getId();
	}
	
	@Test
	void shouldCreateAuthor() {
				
		ResponseAuthorDto response = 
				 	RestAssured
				 	.given()
				 		.header("X-User-Id", UUID.randomUUID())
				 		.contentType(ContentType.JSON)
				 		.body(TestFactory.validAuthor())
				 	.when()
				 		.post("/api/authors/register")
				     .then()
				     	.statusCode(200)
				     	.extract()
				     	.as(ResponseAuthorDto.class)
				     	;
				 		
		assertEquals("Himanshu Kushwaha", response.penName()); 	
		assertEquals( "Backend engineer and microservices enthusiast writing about distributed systems.", response.bio());
		assertNotNull(response.id());
	}
	
	@Test
	void shouldGetAuthor_success() {
		ResponseAuthorDto response = 
			 	RestAssured
			 	.given()
			 		.header("X-User-Id", keycloakId)
			 	.when()
			 		.get("/api/authors/get/{authorId}", authorId)
			     .then()
			     	.statusCode(200)
			     	.extract()
			     	.as(ResponseAuthorDto.class)
			     	;
		assertEquals("Himanshu", response.penName()); 	
		assertEquals( "Backend engineer and microservices enthusiast writing about distributed systems.", response.bio());
		assertEquals(authorId, response.id());
	}
	
	@Test
	void shouldGetAllAuthor_success() {
		List<ResponseAuthorDtoForAdmin> response = 
			 	RestAssured
			 	.given()
			 	.when()
			 		.get("/api/authors/gets")
			     .then()
			     	.statusCode(200)
			     	.extract()
			     	.jsonPath()
                    .getList(".", ResponseAuthorDtoForAdmin.class);
		
	    assertEquals(1, response.size());
     	
	}
	
	@Test
	void shouldUpdate_success() {
		ResponseAuthorDto response = 
			 	RestAssured
			 	.given()
			 		.header("X-User-Id", keycloakId)
			 		.contentType(ContentType.JSON)
			 		.body(TestFactory.validAuthorUpdate())
			 	.when()
			 		.patch("/api/authors/update/{authorId}", authorId)
			     .then()
			     	.statusCode(200)
			     	.extract()
			     	.as(ResponseAuthorDto.class)
			     	;
		assertEquals("Kushwaha", response.penName()); 	
		assertEquals( "nope nope nope", response.bio());
		assertEquals(authorId, response.id());	
		Author author = authorRepo.findById(authorId).orElseThrow();

		assertEquals("Kushwaha", author.getPenName()); 	
		assertEquals( "nope nope nope", author.getBio());
	}
	
	@Test
	void shouldDelete() {
		RestAssured.
			given()
	 			.header("X-User-Id", keycloakId)
	 		.when()
	 			.delete("/api/authors/delete/{authorId}", authorId)
	 		.then()
	 			.statusCode(200)
	 			;
		
		Author author = authorRepo.findById(authorId).orElseThrow();
		assertTrue(author.isDeleted());
	}
	
}
