package com.el_jobru.service;

import com.el_jobru.dto.book.BookResponseDTO;
import com.el_jobru.dto.book.RegisterBookDTO;
import com.el_jobru.models.book.Book;
import com.el_jobru.repository.BookRepository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book registerBook(RegisterBookDTO bookDTO) throws Exception {

        Book book = new Book(bookDTO.title(), bookDTO.author());

        return bookRepository.save(book);
    }

    public List<BookResponseDTO> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();

        return bookList.stream()
                .map(BookResponseDTO::new)
                .collect(Collectors.toList());
    }

}
