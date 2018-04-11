package com.example.demo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Book {

    @Id
    var id: String? = null

    var title: String? = null

    var writer: String? = null
}
