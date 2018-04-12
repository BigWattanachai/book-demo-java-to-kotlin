package com.example.demo


import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface BookRepository : ReactiveMongoRepository<Book, String> {

    fun findByTitleIgnoringCase(title: String): Mono<Book>

}
