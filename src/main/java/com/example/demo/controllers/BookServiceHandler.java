package com.example.demo.controllers;

import com.example.demo.data.Author;
import com.example.demo.data.Book;
import com.example.demo.data.Student;
import com.example.demo.data.enriched.EnrichedBook;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookServiceHandler {

    private final BookService bookService;
    private final AuthorService authorService;
    private final StudentService studentService;

    @GetMapping("/api/availability")
    public ResponseEntity<String> checkService() {
        return new ResponseEntity<>("Service is up and running..", HttpStatus.OK);
    }

    @GetMapping("/BookService/Books")
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/BookService/Books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        return new ResponseEntity<>(bookService.getBook(Integer.parseInt(id)), HttpStatus.OK);
    }

    @GetMapping("/BookService/Authors")
    public ResponseEntity<List<Author>> getAuthors() {
        return new ResponseEntity<>(authorService.getAuthors(), HttpStatus.OK);
    }

    @GetMapping("/BookService/Authors/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable String id) {
        return new ResponseEntity<>(authorService.getAuthor(Integer.parseInt(id)), HttpStatus.OK);
    }

    @GetMapping("/BookService/Students")
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/BookService/Students/{id}")
    public ResponseEntity<Student> getStudents(@PathVariable String id) {
        return new ResponseEntity<>(studentService.getStudent(Integer.parseInt(id)), HttpStatus.OK);
    }

    @PutMapping("/BookService/Books/{bookId}/Authors/{authorId}")
    public ResponseEntity<Book> AssignBookToAuthor(@PathVariable String bookId, @PathVariable String authorId) {
        return new ResponseEntity<>(bookService.AddAuthorToBook(Integer.parseInt(bookId), Integer.parseInt(authorId)), HttpStatus.OK);
    }

    @PutMapping("/BookService/Books/{bookId}/Students/{studentId}")
    public ResponseEntity<Book> AssignBookToStudent(@PathVariable String bookId, @PathVariable String studentId) {
        return new ResponseEntity<>(bookService.AddStudentToBook(Integer.parseInt(bookId), Integer.parseInt(studentId)), HttpStatus.OK);
    }

    @GetMapping("/BookService/EnrichedBook/{id}")
    public ResponseEntity<EnrichedBook> getEnrichedBook(@PathVariable String id){
        return new ResponseEntity<>(bookService.getEnrichedBook(Integer.parseInt(id)), HttpStatus.OK);
    }
}
