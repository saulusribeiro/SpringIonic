package br.com.saulusribeiro.springbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.saulusribeiro.springbackend.services.S3Service;

@SpringBootApplication
public class SpringbackendApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbackendApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		  s3Service.uploadFile("C:\\temp\\fotos\\imagem-teste.jpg");
		}

}
