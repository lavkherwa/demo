package com.example.demo.service.impl;

import com.example.demo.client.BookDetailServiceClient;
import com.example.demo.data.Book;
import com.example.demo.data.enriched.BookDetail;
import com.example.demo.data.enriched.EnrichedBook;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final StudentRepository studentRepo;
    private final BookDetailServiceClient bookDetailServiceAdapter;

    public List<Book> getBooks() {

        var books = (List<Book>) bookRepo.findAll();
        if (!books.isEmpty())
            return books;

        throw new DataNotFoundException("No Books found");
    }

    @Override
    public Book getBook(int id) {
        if (bookRepo.findById(id).isPresent())
            return bookRepo.findById(id).get();

        throw new DataNotFoundException(String.format("Book not found with ID: %d", id));
    }

    @Override
    public Book AddAuthorToBook(int bookId, int authorId) {

        // Read book
        if (bookRepo.findById(bookId).isEmpty())
            throw new DataNotFoundException(String.format("Book not found with ID: %d", bookId));
        var book = bookRepo.findById(bookId).get();

        // Read Author

        if (authorRepo.findById(authorId).isEmpty())
            throw new DataNotFoundException(String.format("Author not found with ID: %d", authorId));
        var author = authorRepo.findById(authorId).get();

        book.getAuthors().add(author);

        return bookRepo.save(book);
    }

    @Override
    public Book AddStudentToBook(int bookId, int studentId) {
        // Read book
        if (bookRepo.findById(bookId).isEmpty())
            throw new DataNotFoundException(String.format("Book not found with ID: %d", bookId));
        var book = bookRepo.findById(bookId).get();

        // Read student
        if (studentRepo.findById(studentId).isEmpty())
            throw new DataNotFoundException(String.format("Student not found with ID: %d", studentId));
        var student = studentRepo.findById(studentId).get();

        book.setStudent(student);

        return bookRepo.save(book);
    }

    @Override
    public EnrichedBook getEnrichedBook(int id) {

        EnrichedBook eBook = new EnrichedBook();

        Book book = null;
        if (bookRepo.findById(id).isPresent())
            book = bookRepo.findById(id).get();

        BookDetail bookDetail = null;
        if (bookDetailServiceAdapter.getBookDetail(id).isPresent())
            bookDetail = bookDetailServiceAdapter.getBookDetail(id).get();


        if (book != null) {
            eBook.setId(book.getID());
            eBook.setName(book.getName());
        }

        if (bookDetail != null) {
            eBook.setAbout(bookDetail.getAbout());
            eBook.setCost(bookDetail.getCost());
            eBook.setYearOfPublish(bookDetail.getYearOfPublish());
        }

        return eBook;
    }


}
