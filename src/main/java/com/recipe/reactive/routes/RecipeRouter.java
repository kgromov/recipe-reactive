package com.recipe.reactive.routes;

import com.recipe.reactive.domain.Recipe;
import com.recipe.reactive.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class RecipeRouter {
    private final RecipeRepository recipeRepository;

    @Bean
    public RouterFunction<?> recipeRoutes() {

        return route()
                .GET("/recipes", accept(APPLICATION_JSON), request -> {
                    Flux<Recipe> recipes = recipeRepository.findAll();
                    return ok()
                            .contentType(APPLICATION_JSON)
                            .body(fromPublisher(recipes, Recipe.class));
//            return ok().bodyValue(recipes);
                })
                .GET("/recipes/{recipeName}", accept(APPLICATION_JSON), request -> {
                    String recipeName = request.pathVariable("recipeName");
                    Mono<Recipe> recipe = recipeRepository.findByDescription(recipeName);
                    return ok()
                            .contentType(APPLICATION_JSON)
                            .body(recipe, Recipe.class);
                })
                .build();
    }

}
