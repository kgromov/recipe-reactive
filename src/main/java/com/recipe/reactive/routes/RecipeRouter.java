package com.recipe.reactive.routes;

import com.recipe.reactive.domain.Recipe;
import com.recipe.reactive.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class RecipeRouter {
    private final RecipeRepository recipeRepository;

    @Bean
    public RouterFunction<?> recipeRoutes() {

        return route(GET("/recipes"),
                request -> {
                    Flux<Recipe> recipes = recipeRepository.findAll();
                    return ok().body(fromPublisher(recipes, Recipe.class));
//            return ok().bodyValue(recipes);
                })
                .and(route(GET("/recipes/{recipeName}"),
                        request -> {
                            String recipeName = request.pathVariable("recipeName");
                            Mono<Recipe> recipe = recipeRepository.findByDescription(recipeName);
                            return ok().body(recipe, Recipe.class);
                        })
                );
    }

}
