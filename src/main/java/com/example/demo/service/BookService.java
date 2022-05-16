package com.example.demo.service;

import com.example.demo.data.Book;
import com.example.demo.data.enriched.EnrichedBook;

import java.util.List;
import java.util.Optional;

public interface BookService {

    public List<Book> getBooks();

    public Book getBook(int id);

    public Book AddAuthorToBook(int bookId, int authorId);

    public Book AddStudentToBook(int bookId, int studentId);

    public EnrichedBook getEnrichedBook(int id);
}
