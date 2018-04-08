package com.example.demo;


import org.springframework.data.repository.Repository;

import java.util.List;

public interface BookRepository extends Repository<Book, String> {

    List<Book> findAll();

    Book findByTitleIgnoringCase(String title);

}
