package com.emi.User_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.emi.User_service.entity.Bookmark;
import com.emi.User_service.entity.Review;
import com.emi.User_service.entity.User;
import com.emi.User_service.entity.UserLibrary;
import com.emi.User_service.repository.BookmarkRepo;
import com.emi.User_service.repository.ReviewRepo;
import com.emi.User_service.repository.UserLibraryRepo;
import com.emi.User_service.repository.UserRepo;
import com.emi.User_service.requestDto.UserUpdateRequestDto;
import com.emi.User_service.responseDto.ResponseBookmarkDto;
import com.emi.User_service.responseDto.ResponseReviewDto;
import com.emi.User_service.responseDto.ResponseUserLibraryDto;
import com.emi.User_service.responseDto.UserResponseDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceApplicationTests {

	private UUID userId;
	private UUID libraryId;

	@Autowired
	private  UserLibraryRepo userLibraryRepo;
	
	@Autowired
	private  UserRepo userRepo;

	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
	private BookmarkRepo bookmarkRepo;
	
	private final UUID keycloakId =
	        UUID.fromString("e3a424d2-a524-4f65-bb94-e229f73e30fb");
	
	@LocalServerPort
	private Integer port ;
 	private UUID bookId = UUID.randomUUID(); 
	
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
		userLibraryRepo.deleteAll();
		userRepo.deleteAll();
		reviewRepo.deleteAll();
		bookmarkRepo.deleteAll();
		
	    User user = new User();
	    user.setKeycloakId(keycloakId);
	    user.setDisplayName("Himanshu Kushwaha");
	    user.setProfileImageUrl("https://cdn.library.com/users/profile123.png");
	    user.setBio("Passionate reader and tech enthusiast.");
	    user.setCreatedAt(Instant.now());
	    user.setIsDeleted(false);
	    user = userRepo.save(user);
	    userId=user.getId();
	    
	    UserLibrary lib1 = new UserLibrary();
	    lib1.setKeycloakId(keycloakId);
	    lib1.setBookId(UUID.randomUUID());
	    lib1.setPurchasedAt(Instant.now());
	    
	    UserLibrary lib2 = new UserLibrary();
	    lib2.setKeycloakId(keycloakId);
	    lib2.setBookId(UUID.randomUUID());
	    lib2.setPurchasedAt(Instant.now());
	    
	    userLibraryRepo.saveAll(List.of(lib1, lib2));
	    
	    UserLibrary lib3 = new UserLibrary();
	    lib3.setKeycloakId(keycloakId);
	    lib3.setBookId(UUID.randomUUID());
	    lib3.setPurchasedAt(Instant.now());
	    
	    lib3 = userLibraryRepo.save(lib3);
	    libraryId = lib3.getId();
	    
	    Review r1 = new Review();
	    r1.setKeycloakId(keycloakId);
	    r1.setUserId(userId);
	    r1.setBookId(bookId);
	    r1.setRating(2);
	    r1.setComment("Very bad , i mean why u even made this shit");
	    r1.setCreatedAt(Instant.now());
	    r1 = reviewRepo.save(r1);
	    
	    Bookmark b1= new Bookmark();
	    b1.setBookId(bookId);
	    b1.setChapterId(UUID.randomUUID());
	    b1.setCreatedAt(Instant.now());
	    b1.setKeycloakId(keycloakId);
	    
	    bookmarkRepo.save(b1);
	    
	}
	
	@Test
	void createUser() {


		    UserResponseDto response =
		            RestAssured
		                .given()
		                    .header("X-User-Id", UUID.randomUUID())
		                    .contentType(ContentType.JSON)
		                    .body(TestDataFactory.validUserRequest())
		                .when()
		                    .post("/api/user/create")
		                .then()
		                    .statusCode(200)
		                    .extract()
		                    .as(UserResponseDto.class);
		    
			assertNotNull(response.id());
		    assertEquals("Himanshu Kushwaha", response.displayName());
		    assertEquals("https://cdn.library.com/users/profile123.png", response.imageurl());
		    assertEquals("Passionate reader and tech enthusiast.", response.bio());
		    assertNotNull(response.createdAt());
		    
	}
	
	@Test
	void createUserLibrary() {
		String request = """
				{
						"bookId": "e3a424d2-a524-4f65-bb94-e229f73e30fb"			
				}
				""";

		ResponseUserLibraryDto response = 
						RestAssured
						    .given()
						    .header("X-User-Id", keycloakId)
						    .contentType("application/json")
						    .body(request)
						    .when()
						    	.post("/api/user/userlibrary/create")
						    .then()
						    	.statusCode(200)
								.extract()
								.as(ResponseUserLibraryDto.class);
		
		assertNotNull(response.id());
		assertEquals(UUID.fromString("e3a424d2-a524-4f65-bb94-e229f73e30fb"), response.bookId());
		assertNotNull(response.purchasedAt());
					
	}
	
	
	@Test
	void getAllUserLibrary() {

	    List<ResponseUserLibraryDto> response =
	            RestAssured
	                .given()
	                    .header("X-User-Id", keycloakId.toString())
	                .when()
	                    .get("/api/user/userlibrary/get")
	                .then()
	                    .statusCode(200)
	                    .extract()
	                    .jsonPath()
	                    .getList(".", ResponseUserLibraryDto.class);

	    assertEquals(3, response.size());
	    assertNotNull(response.get(0).bookId());
	    assertNotNull(response.get(0).purchasedAt());
	}
	
	@Test
	void deleteUserLibrary() {
				RestAssured
						.given()
							.header("X-User-Id", keycloakId.toString())
						.when()
							.delete("/api/user/userlibrary/delete/{id}", libraryId)
						.then()
							.statusCode(200)
							;
				
		assertFalse(userLibraryRepo.findById(libraryId).isPresent());
	}
	
	
	@Test
	void deleteUserLibrary_wrongUser_shouldFail() {

	    UUID otherUserId = UUID.randomUUID();

	    RestAssured
	            .given()
	                .header("X-User-Id", otherUserId.toString())
	            .when()
	                .delete("/api/user/userlibrary/delete/{id}", libraryId)
	            .then()
	                .statusCode(404); 

	    
	    assertTrue(userLibraryRepo.findById(libraryId).isPresent());
	}
	
	@Test
	void deleteUserLibrary_notFound() {

	    UUID randomId = UUID.randomUUID();

	    RestAssured
	            .given()
	                .header("X-User-Id", keycloakId.toString())
	            .when()
	                .delete("/api/user/userlibrary/delete/{id}", randomId)
	            .then()
	                .statusCode(404);
	}
	
	@Test
	void getUser() {
		UserResponseDto response = 
						RestAssured
							.given()
								.header("X-User-Id", keycloakId.toString())
							.when()
								.get("/api/user/get/{id}", userId)
							.then()
		                    	.statusCode(200)
							.extract()
							.as(UserResponseDto.class);
		
		assertEquals(userId, response.id());
		assertEquals("Himanshu Kushwaha", response.displayName());
	    assertEquals("https://cdn.library.com/users/profile123.png", response.imageurl());
	    assertEquals("Passionate reader and tech enthusiast.", response.bio());
	    assertNotNull(response.createdAt());
	}

	@Test
	void updateUser() {
	    UserUpdateRequestDto request = new UserUpdateRequestDto(
	            userId,
	            "New Name",
	            "https://new.png",
	            "New bio"
	    );
	    UserResponseDto response =
	            RestAssured
	                .given()
	                    .header("X-User-Id", keycloakId)
	                    .contentType(ContentType.JSON)
	                    .body(request)
	                .when()
	                    .patch("/api/user/update")
	                .then()
	                    .statusCode(200)
	                    .extract()
	                    .as(UserResponseDto.class);
	    
	    assertEquals("New Name", response.displayName());
	    assertEquals("https://new.png", response.imageurl());
	    assertEquals("New bio", response.bio());
	    
	    User updated = userRepo.findById(userId).orElseThrow();
	    assertEquals("New Name", updated.getDisplayName());
	}
	
	
	@Test
	void updateUser_wrongUser_shouldFail() {

	    UUID otherKeycloakId = UUID.randomUUID();

	    UserUpdateRequestDto request = new UserUpdateRequestDto(
	             userId,
	            "Hacked Name",
	            "https://hack.png",
	            "Hack bio"
	    );

	    RestAssured
	        .given()
	            .header("X-User-Id", otherKeycloakId.toString())
	            .contentType(ContentType.JSON)
	            .body(request)
	        .when()
	            .patch("/api/user/update")
	        .then()
	            .statusCode(500); 
	}
	
	
	@Test
	void deleteUser() {
		
			RestAssured
					.given()
						.header("X-User-Id", keycloakId.toString())
					.when()
						.delete("/api/user/delete/{id}", userId)
					.then()
						.statusCode(200)
						;
			
		User updateUser = userRepo.findById(userId).orElseThrow();
		assertTrue(updateUser.getIsDeleted());
	}
	
	
	@Test
	void deleteUser_wrongUser_shouldFail() {

	    UUID otherUserId = UUID.randomUUID();

	    RestAssured
	            .given()
	                .header("X-User-Id", otherUserId.toString())
	            .when()
	                .delete("/api/user/delete/{id}", userId)
	            .then()
	                .statusCode(500); 

		User updateUser = userRepo.findById(userId).orElseThrow();
		assertFalse(updateUser.getIsDeleted());
	}
	
	@Test
	void deleteUser_UsernotFound() {

	    UUID randomId = UUID.randomUUID();

	    RestAssured
	            .given()
	                .header("X-User-Id", keycloakId.toString())
	            .when()
	                .delete("/api/user/delete/{id}", randomId)
	            .then()
	                .statusCode(404);
	}
	
	@Test
	void createReview() {
		ResponseReviewDto response = 
			    RestAssured 
				    	.given()
		                   .header("X-User-Id", keycloakId.toString())
		                   .contentType(ContentType.JSON)
		                   .body(TestDataFactory.validReviewRequest())
		               .when()
		                   .post("/api/user/review/create")
		               .then()
		                   .statusCode(200)
		                   .extract()
		                   .as(ResponseReviewDto.class);
		
		assertEquals(UUID.fromString("e3a424d2-a524-4f65-bb94-e229f73e30fb"), response.bookId());
		assertEquals(userId, response.userId());
		assertEquals(1, response.rating());
		assertEquals("Very bad , i mean why u even made this shit", response.comment());
		assertNotNull(response.createdAt());
		assertNotNull(response.id());
	}
	
	@Test
	void getReview() {
		 List<ResponseReviewDto> response =
		            RestAssured
		                .when()
		                    .get("/api/user/review/{bookId}", bookId)
		                .then()
		                    .statusCode(200)
		                    .extract()
		                    .jsonPath()
		                    .getList(".", ResponseReviewDto.class);
		 
		    assertEquals(1, response.size());
		    assertNotNull(response.get(0).bookId());
	}
	
	void createBookmark() {
		
		String request ="""
				{
				  "bookId":"e3a424d2-a524-4f65-bb94-e229f73e30fb",
				  "chapterId":"b8f9c4a1-3c2d-4e89-91d3-0a1234567890"
				}
				""";
		ResponseBookmarkDto response = 
			    RestAssured 
				    	.given()
		                   .header("X-User-Id", keycloakId.toString())
		                   .contentType(ContentType.JSON)
		                   .body(request)
		               .when()
		                   .post("/api/user/bookmark/create")
		               .then()
		                   .statusCode(200)
		                   .extract()
		                   .as(ResponseBookmarkDto.class);
		
		assertEquals(UUID.fromString("e3a424d2-a524-4f65-bb94-e229f73e30fb"), response.bookId());
		assertEquals(UUID.fromString("b8f9c4a1-3c2d-4e89-91d3-0a1234567890"), response.chapterID());
	}
	
	@Test
	void getBookmark() {
		 List<ResponseBookmarkDto> response =
		            RestAssured
		            	.given()
		            		.header("X-User-Id", keycloakId.toString())
		                .when()
		                    .get("/api/user/bookmark/get")
		                .then()
		                    .statusCode(200)
		                    .extract()
		                    .jsonPath()
		                    .getList(".", ResponseBookmarkDto.class);
		 
		    assertEquals(1, response.size());
		    assertNotNull(response.get(0).bookId());
	}
	
	
}
