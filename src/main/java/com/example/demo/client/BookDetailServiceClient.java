package com.example.demo.client;

import com.example.demo.data.enriched.BookDetail;

import java.util.Optional;


public interface BookDetailServiceClient {

    public Optional<BookDetail> getBookDetail(int id);

}
