package com.desafioConsumoApi.Books.finder.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData( @JsonAlias("id") Long id,
                        @JsonAlias("title") String title,
                        @JsonAlias("authors") List<AuthorData> authors,
                        @JsonAlias("languages") List<String> languages,
                        @JsonAlias("copyright") String copyright,
                        @JsonAlias("download_count") Integer downloadCount) {

}
