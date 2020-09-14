package br.com.charlesalves.sincronizacaoreceita.steps.importdata;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.charlesalves.sincronizacaoreceita.domain.Account;

@Configuration
public class ImportWriterConfig {

	@Bean
	public JpaItemWriter<Account> importWriter(EntityManagerFactory entityManagerFactory) {
		return new JpaItemWriterBuilder<Account>()
			.entityManagerFactory(entityManagerFactory)
			.build();
	}
}
