package br.com.saulusribeiro.springbackend;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.saulusribeiro.springbackend.domain.Categoria;
import br.com.saulusribeiro.springbackend.repositories.CategoriaRepository;

@SpringBootApplication
public class SpringbackendApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringbackendApplication.class, args);
	}
	
	@Autowired
	CategoriaRepository categoriaRepository;
	

	@Override
	public void run(String... args) throws Exception {
		
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		
	}

}
