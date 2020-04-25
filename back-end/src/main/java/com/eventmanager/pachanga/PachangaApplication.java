package com.eventmanager.pachanga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PachangaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PachangaApplication.class, args);

		// A BUILD NÃO FUNCIONA POIS NÃO TEM URL DE BANCO DE DADOS. DEVEMOS CONFIGURAR UM H2 DE LEVE OU COLOCAR O POSTGRE


	}

}
