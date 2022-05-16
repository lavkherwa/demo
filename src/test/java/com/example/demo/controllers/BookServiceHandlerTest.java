package com.example.demo.controllers;

import com.example.demo.data.Author;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.StudentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = BookServiceHandler.class)
public class BookServiceHandlerTest {

    private static final List<Author> authors = List.of(
            new Author(1, "author 1", new ArrayList<>()),
            new Author(2, "author 2", new ArrayList<>()),
            new Author(3, "author 3", new ArrayList<>())
    );
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private StudentService studentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test should pass if all the authors are fetched")
    void getAuthors() throws Exception {

        Mockito.when(authorService.getAuthors()).thenReturn(authors);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/BookService/Authors"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("author 1")));


    }

}