package com.recipe.reactive.repositories;

import com.recipe.reactive.domain.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository testee;

  /*  Flux<Profile> saved = repository.saveAll(Flux.just(new Profile(null, "Josh"), new Profile(null, "Matt"), new Profile(null, "Jane")));
    Predicate<Profile> match = profile -> saved.any(saveItem -> saveItem.equals(profile)).block();

        StepVerifier
            .create(composite)
            .expectNextMatches(match)
            .expectNextMatches(match)
            .expectNextMatches(match)
            .verifyComplete();*/


//  repository.save(new Account(null, "Bill", 12.3)).block();
//    Flux<Account> accountFlux = repository.findAllByValue(12.3);
//
//    StepVerifier
//            .create(accountFlux)
//            .assertNext(account -> {
//        assertEquals("Bill", account.getOwner());
//        assertEquals(Double.valueOf(12.3) , account.getValue());
//        assertNotNull(account.getId());
//    })
//            .expectComplete()
//      .verify();


    @BeforeEach
    void setUp() {
        Recipe recipe = new Recipe("Sushi");
        testee.deleteAll()
                .then(
                        Mono.just(recipe)
                                .flatMap(testee::save)
                )
                .block();
    }

    @Test
    void findByDescription() {
        Mono<Recipe> recipe = testee.findByDescription("Sushi");

        StepVerifier.create(recipe)
                .assertNext(r -> {
                    assertEquals("Sushi", r.getDescription());
                    assertNotNull(r.getId());
                })
                .verifyComplete();
    }

    @Test
    public void anotherTest() {
        Recipe pizza = new Recipe("Pizza " + UUID.randomUUID());
        Mono<Recipe> recipe = testee.save(pizza)
                .map(Recipe::getId)
                .flatMap(testee::findById);

        StepVerifier.create(recipe)
                .assertNext(r -> {
                    assertEquals(pizza.getDescription(), r.getDescription());
                    assertNotNull(r.getId());
                })
                .verifyComplete();
    }
}