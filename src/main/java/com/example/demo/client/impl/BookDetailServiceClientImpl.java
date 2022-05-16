package com.example.demo.client.impl;

import com.example.demo.client.BookDetailServiceClient;
import com.example.demo.data.enriched.BookDetail;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookDetailServiceClientImpl implements BookDetailServiceClient {

    private static final String OUTBOUND_SERVICE = "outbound-service";

    @Value("${bookdetails_service_url}")
    private String BASE_URL;
    private final RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = OUTBOUND_SERVICE)
    @Retry(name = OUTBOUND_SERVICE, fallbackMethod = "bookDetailServiceFallback")
    public Optional<BookDetail> getBookDetail(int id) {

        System.out.println(String.format("Fetching Details for Book ID %s from Demo2 Service", id));

        final var url = buildServiceUrl(id);
        final RequestEntity<Void> requestEntity =
                RequestEntity.get(url).headers(initHeaders()).build();

        ResponseEntity<BookDetail> response;

        try {
            response = restTemplate.exchange(requestEntity, BookDetail.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.out.println(String.format(
                            "ERROR: Call to Book detail service failed with Error `%s` on application URL `%s`; Status Code Text `%s`; Response Body `%s`",
                            ex.getMessage(),
                            url,
                            ex.getStatusText(),
                            ex.getResponseBodyAsString()));
            throw ex;
        }

        return Optional.ofNullable(response.getBody());
    }

    public Optional<BookDetail> bookDetailServiceFallback(Exception exp) {

        System.out.println("Fetch failed to get book details, falling back to default values");
        return Optional.of(new BookDetail());
    }

    private String buildServiceUrl(int id) {
        return UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .path("/api/Books/{id}")
                .buildAndExpand(Map.of("id", id))
                .toUri()
                .toString();
    }

    private HttpHeaders initHeaders() {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
