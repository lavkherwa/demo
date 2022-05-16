package com.example.demo.service.impl;

import com.example.demo.client.BookDetailServiceClient;
import com.example.demo.client.impl.BookDetailServiceClientImpl;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookServiceImplTest {

    @Test
    @DisplayName("Test should throw exception when no students found")
    void shouldFailWhenNoAuthorsFound() {
        BookRepository bookRepo = Mockito.mock(BookRepository.class);
        AuthorRepository authorRepo = Mockito.mock(AuthorRepository.class);
        StudentRepository studentRepo = Mockito.mock(StudentRepository.class);
        BookDetailServiceClient bookDetailServiceAdapter = Mockito.mock(BookDetailServiceClientImpl.class);

        BookService bookService = new BookServiceImpl(bookRepo, authorRepo, studentRepo, bookDetailServiceAdapter);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, bookService::getBooks);

        assertThat(exception.getMessage().equals("No Books found")).isTrue();

    }

    @Test
    @DisplayName("Test should throw exception when no student is found")
    void shouldFailWhenNoAuthorFound() {
        BookRepository bookRepo = Mockito.mock(BookRepository.class);
        AuthorRepository authorRepo = Mockito.mock(AuthorRepository.class);
        StudentRepository studentRepo = Mockito.mock(StudentRepository.class);
        BookDetailServiceClient bookDetailServiceAdapter = Mockito.mock(BookDetailServiceClientImpl.class);

        BookService bookService = new BookServiceImpl(bookRepo, authorRepo, studentRepo, bookDetailServiceAdapter);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () ->
                bookService.getBook(1)
        );

        assertThat(exception.getMessage().equals("Book not found with ID: 1"));
    }
}