package com.desafioConsumoApi.Books.finder.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorData(@JsonAlias("name") String name,
                         @JsonAlias("birth_year") Integer dateOfBirth,
                         @JsonAlias("death_year") Integer dateOfDecease) {
}
