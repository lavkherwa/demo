package com.example.demo.client.impl;

import com.example.demo.client.config.WireMockInitializer;
import com.example.demo.data.enriched.BookDetail;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {WireMockInitializer.class})
class BookDetailServiceClientImplTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private BookDetailServiceClientImpl bookDetailServiceClient;

    @AfterEach
    public void resetAll() {
        wireMockServer.resetAll();
    }

    @Test
    @DisplayName("Test should pass when book detail service should response with 200 OK")
    public void bookDetailService200() {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/api/Books/1"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBodyFile("book-detail-service/bookdetail-response-200.json")
                        )
        );

        BookDetail bookDetail = bookDetailServiceClient.getBookDetail(1).get();

        Assertions.assertThat(bookDetail.getAbout()).isEqualTo("About book1");
        Assertions.assertThat(bookDetail.getCost()).isEqualTo(34.0);
        Assertions.assertThat(bookDetail.getYearOfPublish()).isEqualTo("2020");
    }

    @Test
    @DisplayName("Test should pass when book detail service response with 500 INTERNAL SERVER ERROR")
    public void bookDetailService500() {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/api/Books/1"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        )
        );

        BookDetail bookDetail = bookDetailServiceClient.getBookDetail(1).get();

        Assertions.assertThat(bookDetail.getAbout()).isEqualTo(null);
        Assertions.assertThat(bookDetail.getCost()).isEqualTo(0.0);
        Assertions.assertThat(bookDetail.getYearOfPublish()).isEqualTo(null);
    }

    @Test
    @DisplayName("Test should pass when empty Book response is returned")
    public void bookDetailServiceFallback() {
        Optional<BookDetail> bookDetail = bookDetailServiceClient.bookDetailServiceFallback(null);

        Assertions.assertThat(bookDetail.get())
                .isInstanceOf(BookDetail.class)
                .usingRecursiveComparison()
                .isEqualTo(new BookDetail());
    }
}