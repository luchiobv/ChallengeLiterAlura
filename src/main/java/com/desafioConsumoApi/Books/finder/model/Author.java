package com.desafioConsumoApi.Books.finder.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer dateOfBirth;
    private Integer dateOfDecease;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author() {
    }

    //constructor for Author with parameter AuthorData
    public Author(AuthorData authorData){
        this.name=authorData.name();
        this.dateOfBirth=authorData.dateOfBirth();
        this.dateOfDecease=authorData.dateOfDecease();
    }

    //Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Integer dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getDateOfDecease() {
        return dateOfDecease;
    }

    public void setDateOfDecease(Integer dateOfDecease) {
        this.dateOfDecease = dateOfDecease;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        books.forEach(b -> b.setAuthor(this));
        this.books = books;
    }

    //to Strong for Author
    @Override
    public String toString() {
        return   "id=" + id +'\'' +
                ", Name='" + name + '\'' +
                ", Date Of Birth=" + dateOfBirth +'\'' +
                ", Date Of Decease=" + dateOfDecease;
    }
}
