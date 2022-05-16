package com.example.demo.repositories;

import com.example.demo.data.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {


}
