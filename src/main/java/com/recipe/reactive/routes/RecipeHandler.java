package com.recipe.reactive.routes;

import com.recipe.reactive.domain.Recipe;
import com.recipe.reactive.domain.dtos.RecipeDto;
import com.recipe.reactive.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
@RequiredArgsConstructor
public class RecipeHandler {
    private final RecipeRepository recipeRepository;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<RecipeDto> recipes = recipeRepository.findAll()
            .flatMap(r -> Mono.just(new RecipeDto(r)));
        return readResponse(recipes);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String recipeId = recipeId(request);
        Mono<RecipeDto> recipe = recipeRepository.findById(recipeId)
            .map(RecipeDto::new);
        return readResponse(recipe);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<RecipeDto> newRecipe = request.bodyToMono(Recipe.class)
            .flatMap(recipeRepository::save)
            .map(RecipeDto::new);
           /* .flatMap(r -> ServerResponse
                .created(URI.create("/recipes/" + r.getId()))
                .contentType(APPLICATION_JSON)
                .build()
            ));*/
        return readResponse(newRecipe);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String recipeId = recipeId(request);
        Mono<RecipeDto> updatedRecipe = request.bodyToMono(Recipe.class)
            .flatMap(body -> recipeRepository.findById(recipeId)
                .map(recipe -> {
                    recipe.setDescription(body.getDescription());
                    return recipe;
                })
            )
            .flatMap(recipeRepository::save)
            .map(RecipeDto::new);
        return readResponse(updatedRecipe);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String recipeId = recipeId(request);
        return ok().build(recipeRepository.deleteById(recipeId));
    }

    private String recipeId(ServerRequest request) {
        return request.pathVariable("recipeId");
    }

    private Mono<ServerResponse> readResponse(Publisher<RecipeDto> recipes) {
        return ok()
            .contentType(APPLICATION_JSON)
            .body(recipes, RecipeDto.class);
    }
}
