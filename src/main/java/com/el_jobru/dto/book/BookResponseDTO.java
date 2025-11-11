package com.el_jobru.dto.book;

import com.el_jobru.models.book.Book;

public record BookResponseDTO(long id, String title, String author) {
    public BookResponseDTO(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor());
    }
}
