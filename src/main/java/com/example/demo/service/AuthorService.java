package com.example.demo.service;

import com.example.demo.data.Author;
import com.example.demo.data.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorService {

    public List<Author> getAuthors();

    public Author getAuthor(int id);

}
