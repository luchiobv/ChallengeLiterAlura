package com.desafioConsumoApi.Books.finder.repository;

import com.desafioConsumoApi.Books.finder.model.Author;
import com.desafioConsumoApi.Books.finder.model.Book;
import com.desafioConsumoApi.Books.finder.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Author, Long> {

   //@Query("SELECT l FROM book l JOIN l.author a WHERE a.name CONTAINING %:name%")
   Optional<Author> findAuthorByNameContaining(String name);


    @Query("SELECT l FROM Book l JOIN l.author a WHERE l.title LIKE %:bTitle%")
    Optional<Book> getBookContainsEqualsIgnoreCaseTitle(String bTitle);

    @Query("SELECT l From Author a join a.books l")
    List<Book> findBooksByAuthor();

    @Query("SELECT a FROM Author a WHERE a.dateOfDecease > :date ")
    List<Author> getAuthorbyDateOfDecease(Integer date);


    @Query("SELECT l FROM Author a join a.books l WHERE l.language = :language")
    List<Book> findBookByLanguage(Language language);


}