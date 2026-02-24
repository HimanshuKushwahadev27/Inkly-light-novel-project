package com.emi.Search_service.Repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.emi.Search_service.document.BookDocument;

public interface BookDocumentRepo extends ElasticsearchRepository<BookDocument, String> {

	BookDocument findByBookId(String string);

}
