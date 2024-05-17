package com.desafioConsumoApi.Books.finder.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(@JsonAlias("count") Integer total,
                   @JsonAlias("results") List<BookData> books) {
}
