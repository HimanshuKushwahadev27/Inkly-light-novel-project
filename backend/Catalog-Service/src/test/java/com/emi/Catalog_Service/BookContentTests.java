package com.emi.Catalog_Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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

import com.emi.Catalog_Service.Entity.Book;
import com.emi.Catalog_Service.Entity.Book_Content;
import com.emi.Catalog_Service.Entity.Genre;
import com.emi.Catalog_Service.Repository.BookContentRepo;
import com.emi.Catalog_Service.Repository.BookRepository;
import com.emi.Catalog_Service.Repository.GenreRepo;
import com.emi.Catalog_Service.RequestDtos.RequestBookCreationDto;
import com.emi.Catalog_Service.RequestDtos.RequestCreateContentDto;
import com.emi.Catalog_Service.ResponseDtos.ResponseContentDto;
import com.emi.Catalog_Service.Snapshots.AuthorSnapshots;
import com.emi.Catalog_Service.Snapshots.GenreSnapshot;
import com.emi.Catalog_Service.enums.BookChapter_Status;
import com.emi.Catalog_Service.enums.BookLifeCycleStatus;
import com.emi.Catalog_Service.enums.BookVisibilityStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookContentTests {

	
	@Autowired
	private BookContentRepo contentRepo;
	
	@Autowired
	private  BookRepository bookRepo;
	
	@Autowired
	private  GenreRepo genreRepo;
	
	private UUID chapter2Id;
	private UUID chapter1Id;
	private UUID bookId;
	private UUID authorId = UUID.randomUUID();
	
	@LocalServerPort
	private Integer port;
	
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
		contentRepo.deleteAll();
		genreRepo.deleteAll();
		bookRepo.deleteAll();
		
		Genre g1 =new Genre();
		g1.setName("Genre1");
		g1.setDescription("very well defined genre1 is not here");
		
		Genre g2 = new Genre();
		g2.setName("Genre2");
		g2.setDescription("very well defined genre2 is not here");
		
		genreRepo.saveAll(List.of(g1, g2));
		  
	   Book book = new Book();
	    book.setTitle("Spring Boot Internals");
	    book.setDescription("Deep dive into Spring Boot");
	    book.setISBN("9780132334584");
	    book.setPrice(new BigDecimal("499.99"));
	    book.setFreePreview(true);
	    book.setCreatedAt(Instant.now());
	    book.setUpdatedAt(Instant.now());
	    book.setStatusLifecycle(BookLifeCycleStatus.ONGOING);
	    book.setStatusVisible(BookVisibilityStatus.PUBLIC);
	    book.setTotalChapters(2);
	    book.setDeleted(false);
	
	    AuthorSnapshots author = new AuthorSnapshots();
	    author.setId(authorId);
	    author.setName("John Doe");
	
	    book.getAuthorSnapshots().add(author);
	
	    GenreSnapshot gs1 = new GenreSnapshot();
	    gs1.setId(g1.getId());
	    gs1.setName(g1.getName());
	
	    GenreSnapshot gs2 = new GenreSnapshot();
	    gs2.setId(g2.getId());
	    gs2.setName(g2.getName());
	
	    book.getGenreIds().add(gs1);
	    book.getGenreIds().add(gs2);
	
	    book = bookRepo.save(book);
	
	    bookId = book.getId();
	    
        Book_Content chapter1 = new Book_Content();
        chapter1.setBookId(bookId);
        chapter1.setTitle("Chapter 1");
        chapter1.setChapterNumber(1);
        chapter1.setPrice(new BigDecimal("10.00"));
        chapter1.setContent("This is chapter 1 content");
        chapter1.setFreePreview(true);
        chapter1.setStatus(BookChapter_Status.PUBLISHED);
        chapter1.setDeleted(false);
        chapter1.setCreatedAt(Instant.now());
        chapter1.setUpdatedAt(Instant.now());
        chapter1 = contentRepo.save(chapter1);
        
        chapter1Id= chapter1.getId();
        
        Book_Content chapter2 = new Book_Content();
        chapter2.setBookId(bookId);
        chapter2.setTitle("Chapter 2");
        chapter2.setChapterNumber(2);
        chapter2.setPrice(new BigDecimal("15.00"));
        chapter2.setContent("This is chapter 2 content");
        chapter2.setFreePreview(false);
        chapter2.setStatus(BookChapter_Status.PUBLISHED);
        chapter2.setDeleted(false);
        chapter2.setCreatedAt(Instant.now());
        chapter2.setUpdatedAt(Instant.now());
        chapter2 = contentRepo.save(chapter2);
        
        chapter2Id= chapter2.getId();
	}
	

	@Test
	void shouldCreateBookContent() {
		RequestCreateContentDto request = TestFactory.validBookContentRequest(bookId);

		ResponseContentDto response =
		            RestAssured
		                .given()
		                    .contentType(ContentType.JSON)
		                    .body(request)
		                .when()
		                    .post("/api/book/contents/create")
		                .then()
		                    .statusCode(200)
		                    .extract()
		                    .as(ResponseContentDto.class);
		
		assertEquals("Getting Started with Spring Boot", response.title());
		assertEquals(bookId, response.bookId());
		assertEquals(3, response.chapterNumber());	    
	}
	
	@Test
	void createMultipleChapters_success() {

	    List<RequestCreateContentDto> request =
	            TestFactory.validMultipleContents(bookId);

	    List<ResponseContentDto> response =
	            RestAssured
	                .given()
	                    .contentType(ContentType.JSON)
	                    .body(request)
	                .when()
	                    .post("/api/book/contents/createMultiple")
	                .then()
	                    .statusCode(200)
	                    .extract()
	                    .jsonPath()
	                    .getList(".", ResponseContentDto.class);

	    assertEquals(2, response.size());

	    assertEquals(4, response.get(0).chapterNumber());
	    assertEquals(5, response.get(1).chapterNumber());
	}
	
	@Test
	void getBookContentsByContentIds_success() {

	    List<ResponseContentDto> response =
	            RestAssured
	                .given()
	                    .queryParam("contentIds", chapter1Id, chapter2Id)
	                .when()
	                    .get("/api/book/contents/ContentIds")
	                .then()
	                    .statusCode(200)
	                    .extract()
	                    .jsonPath()
	                    .getList(".", ResponseContentDto.class);

	    assertEquals(2, response.size());

	    List<UUID> returnedIds =
	            response.stream()
	                    .map(ResponseContentDto::id)
	                    .toList();

	    assertTrue(returnedIds.contains(chapter1Id));
	    assertTrue(returnedIds.contains(chapter2Id));
	}
	
	@Test
	void getContent_success() {
	    List<ResponseContentDto> response =
	            RestAssured
	                .given()
	                .when()
	                    .get("/api/book/contents/bookId/{bookId}", bookId)
	                .then()
	                    .statusCode(200)
	                    .extract()
	                    .jsonPath()
	                    .getList(".", ResponseContentDto.class);

	    assertEquals(2, response.size());

	    List<UUID> returnedIds =
	            response.stream()
	                    .map(ResponseContentDto::id)
	                    .toList();

	    assertTrue(returnedIds.contains(chapter1Id));
	    assertTrue(returnedIds.contains(chapter2Id));
	}
	
	@Test
	void deleteBookContentByContentIds_success() {

	    RestAssured
	        .given()
	            .queryParam("contentId", chapter1Id, chapter2Id)
	        .when()
	            .delete("/api/book/contents/contentIds/{authorId}", authorId)
	        .then()
	            .statusCode(200);

	    
	    assertTrue(
	    	    contentRepo.findById(chapter1Id).orElseThrow().isDeleted()
	    	);
	}
	
	@Test
	void deleteBookContentByBookId_success() {

	    RestAssured
	        .when()
	            .delete("/api/book/contents/bookId/{bookId}/{authorId}", bookId, authorId)
	        .then()
	            .statusCode(200);

	    List<Book_Content> remaining =
	            contentRepo.findAllByBookId(bookId);

	    remaining.forEach(c ->
	    assertTrue(c.isDeleted()));	
	 }
}
