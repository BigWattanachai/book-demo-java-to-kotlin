package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/books")
    public List<Book> getAllBooks() {
        return this.repository.findAll();
    }

    @GetMapping(path = "/books/{title}")
    public Book byTitle(@PathVariable String title) {
        return this.repository.findByTitleIgnoringCase(title);
    }

}
