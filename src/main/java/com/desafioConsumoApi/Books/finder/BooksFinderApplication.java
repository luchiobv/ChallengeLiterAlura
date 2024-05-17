package com.desafioConsumoApi.Books.finder;

import com.desafioConsumoApi.Books.finder.principal.Principal;
import com.desafioConsumoApi.Books.finder.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksFinderApplication implements CommandLineRunner {

	@Autowired
	private LibraryRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BooksFinderApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(repository);
		principal.showMenu();

	}

}
