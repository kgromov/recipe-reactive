package com.recipe.reactive.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by jt on 6/13/17.
 */
@Getter
@Setter
@Document
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    private String description;
}
