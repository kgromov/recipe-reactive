package com.recipe.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableConfigurationProperties
public class RecipeReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeReactiveApplication.class, args);
    }

}
