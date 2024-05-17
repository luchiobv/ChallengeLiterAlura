package com.desafioConsumoApi.Books.finder.principal;

import com.desafioConsumoApi.Books.finder.model.*;
import com.desafioConsumoApi.Books.finder.repository.LibraryRepository;
import com.desafioConsumoApi.Books.finder.service.ConsumAPI;
import com.desafioConsumoApi.Books.finder.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumAPI consumAPI = new ConsumAPI();
    private ConvertData convert = new ConvertData();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static final String URL_SEARCH = "?search=";
    private final LibraryRepository repository;


    public Principal(LibraryRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - mostrar libros registrados
                    3 - Mostrar Autores registrados
                    4 - Buscar Autores en un determinado año
                    5 - Mostrar libros por idoma
                    6 - Mostrar top 10 de los mejores libros
                    7 - Mostrar estaditicas generales
                    8 - Buscar autor por nombre 
                                        
                                  
                    0 - Salir
                    """;
            while (option != 0) {
                out.println(menu);
                option = scanner.nextInt();
                scanner.nextLine();
                try {

                    switch (option) {
                        case 1:
                            getBookByTitle();
                            break;
                        case 2:
                            showRegisteredBooks();
                            break;
                        case 3:
                            showRegistredAuthors();
                            break;
                        case 4:
                            revealLiveAuthors();

                            break;
                        case 5:
                            findBooksByLanguage();
                            break;
                        case 6:
                            top10Downloads();
                            break;
                        case 7:
                            statistics();
                            break;
                        case 8:
                            searchAuthorByName();
                            break;

                        case 0:
                            break;
                        default:
                            out.println("Invalid option");
                    }
                } catch (NumberFormatException e) {
                    out.println("Invalid option");
                }
            }
        }
    }

    private void getBookByTitle() {
        out.println("Insert the name of the book that you want to search: ");
        var bTitle = scanner.nextLine().replace(" ", "%20");
        var json = consumAPI.gettingData(URL_BASE+URL_SEARCH+ bTitle);
        var bdata= convert.gettingData(json, Data.class);

        Optional <BookData> bookSearch = bdata.books().stream()
                .findFirst();
        if (bookSearch.isPresent()) {
            //out.println(bookSearch);
            out.println(
                    "\n----- BOOK -----\n" +
                    "Title: " + bookSearch.get().title() +
                    "\n Author: " + bookSearch.get().authors().stream().map(a -> a.name()).limit(1).collect(Collectors.joining()) +
                    "\n Language: " + bookSearch.get().languages().stream().collect(Collectors.joining()) +
                    "\n Number of downloads: " + bookSearch.get().downloadCount() +"\n"+
                    "\n-----------------");
            try {
                List<Book> booklook;
                booklook = bookSearch.stream().map(a -> new Book(a)).collect(Collectors.toList());
                Author authorApi = bookSearch.stream()
                        .flatMap(b -> b.authors().stream()
                                .map(a -> new Author(a)))
                        .collect(Collectors.toList()).stream().findFirst().get();

                Optional<Author> authorBd = repository.findAuthorByNameContaining(bookSearch.get().authors().stream()
                        .map(a -> a.name())
                        .collect(Collectors.joining()));

                Optional<Book> optionalBook = repository.getBookContainsEqualsIgnoreCaseTitle(bTitle);


                if (optionalBook.isPresent()) {
                    out.println("Book already save on the Data Base");
                } else {
                    Author author;
                    if (authorBd.isPresent()) {
                        author = authorBd.get();
                        out.println("Author already exist on the Data Base");
                    } else {
                        author = authorApi;
                        repository.save(author);
                    }
                    author.setBooks(booklook);
                    repository.save(author);
                }

            } catch (Exception e) {
                System.out.println("Warning! " + e.getMessage());
            }
        }else{
            out.println("Book not found");
        }

        }

        public void searchAuthorByName(){
            if (repository == null) {
                System.out.println("Repository is not initialized!");
                return;
            }
            out.println("Please enter the name of the author: ");
            var name =scanner.nextLine();
            Optional<Author> author =repository.findAuthorByNameContaining(name);


            if (author.isPresent()){
                System.out.println(
                        "\n----- AUTHOR -----" +
                        "\nAuthor: " + author.get().getName() +
                        "\nDate of birth: " + author.get().getDateOfBirth() +
                        "\nDate of decease: " + author.get().getDateOfDecease() +
                        "\nBooks: " + author.get().getBooks().stream()
                        .map(l -> l.getTitle()).collect(Collectors.toList()) + "\n"+
                        "\n--------------------\n" );
            }else {
                out.println("This author isn´t registered on the Data Base");
            }
        }

        public void showRegisteredBooks(){
        List<Book> books = repository.findBooksByAuthor();
        books.forEach(l -> out.println(
                        "------ BOOK ------" +
                        "\nTitle: " + l.getTitle() +
                        "\nAuthor: " + l.getAuthor().getName() +
                        "\nLanguage: " + l.getLanguage().getIdiom() +
                        "\nNumber of downloads: " + l.getDownloadCount() +
                        "\n------------------"));
        }
        public void showRegistredAuthors() {
            List<Author> authors = repository.findAll();
            authors.forEach(l -> out.println(
                            "\n----- AUTHOR -----" +
                            "\nAuthor: " + l.getName() +
                            "\nDate of birth: " + l.getDateOfBirth() +
                            "\nDete of decease: " + l.getDateOfDecease() +
                            "\n Books: " + l.getBooks().stream()
                            .map(t -> t.getTitle()).collect(Collectors.toList()) + "\n" +
                            "\n------------------------\n"
            ));
        }
        public void revealLiveAuthors(){
            out.println("Type the year that you want to search the live author: ");
            try {
                var date =Integer.valueOf(scanner.nextLine());
                List<Author>authors = repository.getAuthorbyDateOfDecease(date);
                if(!authors.isEmpty()){
                    authors.forEach(l -> out.println(
                            "\n----- AUTHOR -----" +
                                    "\nAuthor: " + l.getName() +
                                    "\nDate of birth: " + l.getDateOfBirth() +
                                    "\nDete of decease: " + l.getDateOfDecease() +
                                    "\n Books: " + l.getBooks().stream()
                                    .map(t -> t.getTitle()).collect(Collectors.toList()) + "\n" +
                                    "\n------------------------\n"
                    ));
                }else {
                    out.println("Sorry! Ee couldn´t find any author on this date"+date);
                }
            } catch (NumberFormatException e) {
                out.println("Please enter only valid date remember use only numbers  ex:1756 "+ e.getMessage());
            }
        }
        public void findBooksByLanguage(){
            var mapLanguages = """
              Select the languages to search books
              
              en - English
              es - Spanish
              fr - French
              it - Italian
              pt - Portuguese
              
              """;
            out.println(mapLanguages);
            var lang = scanner.nextLine().toLowerCase();
            if (lang.equalsIgnoreCase("es")
                    || lang.equalsIgnoreCase("en")
                    || lang.equalsIgnoreCase("it")
                    || lang.equalsIgnoreCase("fr")
                    || lang.equalsIgnoreCase("pt")) {
                Language language= Language.fromString(lang);
                List<Book>books = repository.findBookByLanguage(language);
                if (books.isEmpty()){
                    out.println("Sorry! we don{t have any book registered with this language");
                }else {
                    books.forEach(t-> out.println(
                            "------ BOOK ------" +
                            "\nTitle: " + t.getTitle() +
                            "\nAuthor: " + t.getAuthor().getName() +
                            "\nLanguage: " + t.getLanguage().getIdiom() +
                            "\nNumber of downloads: " + t.getDownloadCount() +
                            "\n------------------"));
                }
            }else{
                out.println("Please Indicate a valid format for a language");
            }
        }

        public void statistics(){
        var json =consumAPI.gettingData(URL_BASE);
        var info = convert.gettingData(json, Data.class);
            IntSummaryStatistics est =info.books().stream()
                    .filter(e -> e.downloadCount() > 0)
                    .collect(Collectors.summarizingInt(BookData::downloadCount));
            Integer average =(int)est.getAverage();
            out.println(
                    "\n---------STATISTICS---------"+
                    "\n Media downloads quantity: " + est.getAverage() +
                    "\n Maximum downloads count: " + est.getMax() +
                    "\nMinimum downloads count: " + est.getMin() +
                    "\nRecords evaluated for calculation: " + est.getCount()

            );
        }

        public void top10Downloads(){
            var json =consumAPI.gettingData(URL_BASE);
            var info = convert.gettingData(json, Data.class);
            info.books().stream()
                .sorted(Comparator.comparing(BookData::downloadCount).reversed())
                .limit(10)
                .map(l -> l.title().toUpperCase())
                .forEach(System.out::println);
        }

    }



