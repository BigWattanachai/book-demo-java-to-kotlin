package com.example.demo

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.bind.Bindable
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.MongoOperations
import java.util.*

@SpringBootApplication
class DemoApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(DemoApplication::class.java, *args)
        }
    }

    private val books: List<Book>
        get() {
            val yaml = loadBooksYaml()
            val source = MapConfigurationPropertySource(yaml)
            return Binder(source).bind("books", Bindable.listOf(Book::class.java)).get()
        }

    @Bean
    fun initData(mongo: MongoOperations) = CommandLineRunner {
        mongo.dropCollection(Book::class.java)
        mongo.createCollection(Book::class.java)
        books.forEach { mongo.save(it) }
    }

    private fun loadBooksYaml(): Properties {
        val properties = YamlPropertiesFactoryBean()
        properties.setResources(ClassPathResource("books.yml"))
        return properties.`object`!!
    }
}
