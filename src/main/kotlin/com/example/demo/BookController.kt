package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(private val repository: BookRepository) {

    @GetMapping("/books")
    fun allBooks() = repository.findAll()

    @GetMapping("/books/{title}")
    fun byTitle(@PathVariable title: String) = repository.findByTitleIgnoringCase(title)

}
