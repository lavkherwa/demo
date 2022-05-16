package com.example.demo.service.impl;

import com.example.demo.data.Author;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.repositories.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @Mock
    AuthorRepository authorRepo;

    @InjectMocks
    AuthorServiceImpl authorService;

    @Captor
    private ArgumentCaptor<Integer> IdArgumentCaptor;

    @Test
    @DisplayName("Test should fetch all the authors")
    void shouldReturnAllAuthors() {

        // Provided
        List<Author> expectedAuthors = List.of(
                new Author(1, "author 1", null),
                new Author(2, "author 2", null),
                new Author(3, "author 3", null)
        );
        // When
        Mockito.when(authorRepo.findAll()).thenReturn(expectedAuthors);
        // Then
        List<Author> actualAuthors = authorService.getAuthors();

        assertThat(actualAuthors).isEqualTo(expectedAuthors);
    }

    @Test
    @DisplayName("Test should fetch the author by ID")
    void shouldReturnAuthorById() {

        // Provided
        Author expectedAuthor = new Author(1, "author 1", null);
        // When
        Mockito.when(authorRepo.findById(1)).thenReturn(Optional.of(expectedAuthor));
        //Then
        Author actualAuthor = authorService.getAuthor(1);

        // Verify findById called twice with ID - 1
        Mockito.verify(authorRepo, Mockito.times(2)).findById(IdArgumentCaptor.capture());

        assertThat(IdArgumentCaptor.getValue()).isEqualTo(1);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("Test should throw exception when no authors found")
    void shouldFailWhenNoAuthorsFound() {

        // Advance using fluent API.
        assertThatThrownBy(authorService::getAuthors)
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("No authors found");
    }

    @Test
    @DisplayName("Test should throw exception when no author is found")
    void shouldFailWhenNoAuthorFound() {

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () ->
                authorService.getAuthor(1)
        );

        assertThat(exception.getMessage().equals("Author not found with ID: 1")).isTrue();
    }
}