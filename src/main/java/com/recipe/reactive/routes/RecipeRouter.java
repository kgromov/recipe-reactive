package com.recipe.reactive.routes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class RecipeRouter {
    private final RecipeHandler recipeHandler;

    @Bean
    public RouterFunction<ServerResponse> recipeRoutes() {
        return route()
            .GET("/recipes", accept(APPLICATION_JSON), recipeHandler::getAll)
            .GET("/recipes/{recipeId}", accept(APPLICATION_JSON), recipeHandler::getById)
            .POST("/recipes", accept(APPLICATION_JSON), recipeHandler::create)
            .PUT("/recipes/{recipeId}", accept(APPLICATION_JSON), recipeHandler::update)
            .DELETE("/recipes/{recipeId}", accept(APPLICATION_JSON), recipeHandler::delete)
            .build();
    }

}
