package com.example.resttemplateclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookClientController {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public BookClientController(RestTemplate restTemplate,
                                @Value("${book.api.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @GetMapping
    public List<Book> getBooks() {
        return restTemplate.exchange(
                baseUrl + "/books",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {}
        ).getBody();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Integer id) {
        return restTemplate.getForObject(baseUrl + "/books/" + id, Book.class);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return restTemplate.postForEntity(baseUrl + "/books", book, Book.class);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Integer id, @RequestBody Book book) {
        HttpEntity<Book> request = new HttpEntity<>(book);
        return restTemplate.exchange(
                baseUrl + "/books/" + id,
                HttpMethod.PUT,
                request,
                Book.class
        ).getBody();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        restTemplate.delete(baseUrl + "/books/" + id);
        return ResponseEntity.noContent().build();
    }
}
