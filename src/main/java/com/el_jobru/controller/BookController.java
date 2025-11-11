package com.el_jobru.controller;

import com.el_jobru.dto.book.BookResponseDTO;
import com.el_jobru.dto.book.RegisterBookDTO;
import com.el_jobru.models.book.Book;
import com.el_jobru.service.BookService;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;

public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void register(Context ctx) {
        try {
            RegisterBookDTO bookDTO = ctx.bodyAsClass(RegisterBookDTO.class);

            Book newBook = bookService.registerBook(bookDTO);

            BookResponseDTO bookResponse = new BookResponseDTO(newBook);

            ctx.status(HttpStatus.OK).json(bookResponse);

        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }

    public void getAll(Context ctx) {
        try {
            List<BookResponseDTO> bookDTO = bookService.getAllBooks();

            ctx.status(HttpStatus.OK).json(bookDTO);
        } catch (Exception e) {
            throw new BadRequestResponse("Erro: " + e);
        }
    }
}
