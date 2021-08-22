package com.recipe.reactive.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Collection;
import java.util.List;

/*
 * For some reason @EnableReactiveMongoRepositories works on Application level only
 */
@Configuration
@EnableReactiveMongoRepositories
public class MongoDbConfig extends AbstractReactiveMongoConfiguration {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients
                .create("mongodb://localhost");
    }

    @Override
    protected String getDatabaseName() {
        return "mongo-reactive";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return List.of("com.recipe.reactive");
    }
}

