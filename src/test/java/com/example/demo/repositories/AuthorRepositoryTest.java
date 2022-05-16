package com.example.demo.repositories;

import com.example.demo.data.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private static final List<Author> authors = List.of(
            new Author(1, "author 1", new ArrayList<>()),
            new Author(2, "author 2", new ArrayList<>()),
            new Author(3, "author 3", new ArrayList<>())
    );

    @BeforeAll
    public void setup() {
        authorRepository.deleteAll();
        authors.forEach(author -> {
            authorRepository.save(author);
        });
    }

    @Test
    @DisplayName("Test should pass when author is fetched for provided ID")
    public void getAuthor() {
        Optional<Author> actualAuthor = authorRepository.findById(1);
        Assertions.assertThat(actualAuthor.get()).usingRecursiveComparison().isEqualTo(authors.get(0));
    }

    @Test
    @DisplayName("Test should pass when all the authors are fetched")
    public void getAuthors() {
        List<Author> actualAuthor = (List<Author>) authorRepository.findAll();
        Assertions.assertThat(actualAuthor.size()).isEqualTo(3);
        Assertions.assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(authors);
    }

}
