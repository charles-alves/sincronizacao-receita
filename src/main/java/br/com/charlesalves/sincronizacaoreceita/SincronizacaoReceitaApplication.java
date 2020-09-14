package br.com.charlesalves.sincronizacaoreceita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SincronizacaoReceitaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SincronizacaoReceitaApplication.class, args);
		int exitStatus = SpringApplication.exit(context);
		System.exit(exitStatus);
	}

}
