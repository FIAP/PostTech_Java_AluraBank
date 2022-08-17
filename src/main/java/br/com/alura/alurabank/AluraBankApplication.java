package br.com.alura.alurabank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluraBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(AluraBankApplication.class, args);
	}

}

/*
	Linter
	Testes automatizados (quanto mais melhor)
	Empacotar a aplicação (*Docker, jar, war, ear)
	Enviar o pacote/artefato para ser armazenado (registry, artfactories)
	Efetuar o deploy do artefato
 */
