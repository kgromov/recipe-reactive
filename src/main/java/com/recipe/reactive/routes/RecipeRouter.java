package com.recipe.reactive.routes;

import com.recipe.reactive.domain.Recipe;
import com.recipe.reactive.domain.dtos.RecipeDto;
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
                    Flux<RecipeDto> recipes = recipeRepository.findAll()
//                            .map(RecipeDto::new)
                            .flatMap(r -> Mono.just(new RecipeDto(r)));
//                    return ok()
//                            .contentType(APPLICATION_JSON)
//                            .body(fromPublisher(recipes, Recipe.class));
                    return ok().body(recipes, RecipeDto.class);
                })
                .GET("/recipes/{recipeId}", accept(APPLICATION_JSON), request -> {
                    String recipeId = request.pathVariable("recipeId");
                    Mono<RecipeDto> recipe = recipeRepository.findById(recipeId)
                            .map(RecipeDto::new);
                    return ok()
                            .contentType(APPLICATION_JSON)
                            .body(recipe, RecipeDto.class);
                })
                .POST("/recipes", accept(APPLICATION_JSON), request -> {
                    Mono<RecipeDto> response = request.bodyToMono(Recipe.class)
                            .flatMap(recipeRepository::save)
                            .map(RecipeDto::new);
//                    return ok().build(recipeRepository.saveRecipe(requestBody));
                    return ok().body(response, RecipeDto.class);
                })
                .PUT("/recipes/{recipeId}", accept(APPLICATION_JSON), request -> {
                    String recipeId = request.pathVariable("recipeId");
                    Mono<RecipeDto> updatedRecipe = request.bodyToMono(Recipe.class)
                            .flatMap(body -> recipeRepository.findById(recipeId)
                                    .map(recipe -> {
                                        recipe.setDescription(body.getDescription());
                                        return recipe;
                                    })
                            )
                            .flatMap(recipeRepository::save)
                            .map(RecipeDto::new);
                    return ok().body(updatedRecipe, RecipeDto.class);
                })
                .DELETE("/recipes/{recipeId}", accept(APPLICATION_JSON), request -> {
                    String recipeId = request.pathVariable("recipeId");
                    return ok().build(recipeRepository.deleteById(recipeId));
                })
                .build();
    }

}
