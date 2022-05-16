package com.example.demo.config.startup;

import com.example.demo.data.Author;
import com.example.demo.data.Book;
import com.example.demo.data.Student;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
@RequiredArgsConstructor
public class ServletStartConfig {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final StudentRepository studentRepo;

    @Bean
    public ServletContextInitializer servletInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(final ServletContext servletContext) throws ServletException {
                try {

                    // Author
                    int authorId = 1;
                    Author author = new Author(authorId, "Lav", null);

                    // Create Author
                    authorRepo.save(author);

                    // Student
                    int studentId = 1;
                    Student student = new Student(studentId, "kush kherwa", null);

                    // Create student
                    studentRepo.save(student);

                    // Book1
                    int book1Id = 1;
                    Book book1 = new Book(book1Id, "book1", null, null);

                    // Book2
                    int book2Id = 2;
                    Book book2 = new Book(book2Id, "book2", null, null);

                    // Create Books
                    bookRepo.save(book1);
                    bookRepo.save(book2);

                } catch (Exception ex) {
                    System.out.println(String.format("Something went wrong %s", ex));
                }
            }
        };
    }


}
