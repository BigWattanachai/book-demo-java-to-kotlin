package com.example.demo;

import lombok.Data;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(MongoOperations mongo) {
        return (String... args) -> {
            mongo.dropCollection(Book.class);
            mongo.createCollection(Book.class);
            getBooks().forEach(mongo::save);
        };
    }

    private List<Book> getBooks() {
        Properties yaml = loadBooksYaml();
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(new PropertiesPropertySource("books", yaml));
        Books books = new Books();
        RelaxedDataBinder binder = new RelaxedDataBinder(books);
        binder.bind(new PropertySourcesPropertyValues(propertySources));
        return books.getBooks();
    }

    private Properties loadBooksYaml() {
        YamlPropertiesFactoryBean properties = new YamlPropertiesFactoryBean();
        properties.setResources(new ClassPathResource("books.yml"));
        return properties.getObject();
    }

    @Data
    private static class Books {
        private List<Book> books = new ArrayList<>();
    }
}
