package com.recipe.reactive.services;

import com.recipe.reactive.domain.Recipe;
import com.recipe.reactive.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * Created by konstantin on 22.08.2021.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final RecipeRepository recipeRepository;

    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        recipeRepository.deleteAll()
                .thenMany(
                        Flux.just("Perfect Guacamole", "Pizza")
                                .map(name -> new Recipe(UUID.randomUUID().toString(), name))
                                .flatMap(recipeRepository::save)
                )
                .thenMany(recipeRepository.findAll())
                .subscribe(recipe -> log.info("saving {}", recipe));
    }
}
