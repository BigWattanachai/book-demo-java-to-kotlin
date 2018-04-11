package com.example.demo


import org.springframework.data.repository.Repository

interface BookRepository : Repository<Book, String> {

    fun findAll(): List<Book>

    fun findByTitleIgnoringCase(title: String): Book

}
