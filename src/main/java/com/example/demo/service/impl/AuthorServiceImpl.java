package com.example.demo.service.impl;

import com.example.demo.data.Author;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepo;

    @Override
    public List<Author> getAuthors() {

        List<Author> authors = (List<Author>) authorRepo.findAll();
        if (!authors.isEmpty())
            return authors;

        throw new DataNotFoundException("No authors found");
    }

    @Override
    public Author getAuthor(int id) {

        if (authorRepo.findById(id).isPresent())
            return authorRepo.findById(id).get();

        throw new DataNotFoundException(String.format("Author not found with ID: %d", id));
    }

}
