package com.emi.Search_service.Dto;

import java.util.List;

public record BookSearchResponse(
        String bookId,
        String title,
        List<String> authorName,
        Double price,
        List<String> genreName,
        Boolean freePreview
) {}