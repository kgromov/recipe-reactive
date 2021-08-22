package com.recipe.reactive.controllers;

import com.recipe.reactive.domain.Recipe;
import com.recipe.reactive.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeRepository recipeRepository;

    @GetMapping("recipes")
    public Flux<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping("recipes/{recipeId}")
    public Mono<Recipe> getRecipe(@PathVariable String recipeId) {
        return recipeRepository.findById(recipeId);
    }

    @GetMapping("recipes/{recipeName}")
    public Mono<Recipe> getRecipeByName(@PathVariable String recipeName) {
        return recipeRepository.findByDescription(recipeName);
    }
}
