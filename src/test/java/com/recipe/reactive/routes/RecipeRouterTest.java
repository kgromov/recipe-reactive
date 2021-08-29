package com.recipe.reactive.routes;

import com.recipe.reactive.domain.Recipe;
import com.recipe.reactive.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest
@Import(RecipeRouter.class)
class RecipeRouterTest {
    @Autowired
    private WebTestClient client;
    @MockBean
    private RecipeRepository recipeRepository;
    private final Recipe sushi = new Recipe(UUID.randomUUID().toString(), "Sushi");
    private final Recipe pizza = new Recipe(UUID.randomUUID().toString(), "Pizza");


    @BeforeEach
    void setUp() {
        when(recipeRepository.findAll()).thenReturn(Flux.just(sushi, pizza));
        when(recipeRepository.findByDescription(sushi.getDescription())).thenReturn(Mono.just(sushi));
    }

    @Test
    void getRecipes() {
        this.client
                .get()
                .uri("/recipes")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(sushi.getId())
                .jsonPath("$.[0].description").isEqualTo(sushi.getDescription())
                .jsonPath("$.[1].id").isEqualTo(pizza.getId())
                .jsonPath("$.[1].description").isEqualTo(pizza.getDescription());
    }

    @Test
    @Disabled
    void getRecipeByName() {
        this.client
                .get()
                .uri("/recipes/" + sushi.getDescription())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(sushi.getId())
                .jsonPath("$.description").isEqualTo(sushi.getDescription());
    }
}