package com.recipe.reactive.domain.dtos;

import com.recipe.reactive.domain.Difficulty;
import com.recipe.reactive.domain.Recipe;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class RecipeDto {
    private String id;
    private String description;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String source;
    private String url;
    private String notes;
    private Difficulty difficulty;

    public RecipeDto(Recipe recipe) {
        this.id = recipe.getId();
        this.description = recipe.getDescription();
        this.source = recipe.getSource();
        this.url = recipe.getUrl();
        this.notes = recipe.getNotes();
        this.difficulty = recipe.getDifficulty();
        this.prepTime = Optional.ofNullable(recipe.getPrepTime()).orElse(0);
        this.cookTime = Optional.ofNullable(recipe.getCookTime()).orElse(0);
        this.servings = Optional.ofNullable(recipe.getServings()).orElse(0);
    }
}
