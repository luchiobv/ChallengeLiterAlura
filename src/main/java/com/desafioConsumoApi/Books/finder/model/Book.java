package com.desafioConsumoApi.Books.finder.model;

import jakarta.persistence.*;

import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
    @Id
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Language language;
    private String copyright;
    private Integer downloadCount;
    @ManyToOne
    private Author author;

    public Book() {
    }

    public Book(BookData bookData){
        this.id = bookData.id();
        this.title = bookData.title();
        this.language = Language.fromString(bookData.languages().stream()
                .limit(1).collect(Collectors.joining()));
        this.copyright = bookData.copyright();
        this.downloadCount = bookData.downloadCount();
    }



    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return
                "id=" + id +'\'' +
                ", Title='" + title + '\'' +
                ", Language=" + language +
                ", Copyright='" + copyright + '\'' +
                ", Download Count=" + downloadCount +'\'' +
                ", Author=" + author;

    }
}
