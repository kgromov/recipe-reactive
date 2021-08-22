package com.recipe.reactive.repositories;

import com.recipe.reactive.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RecipeRepository extends ReactiveMongoRepository<Recipe, String> {
    Mono<Recipe> findByDescription(String description);
}
