package br.com.charlesalves.sincronizacaoreceita.steps.export;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.charlesalves.sincronizacaoreceita.domain.Account;

@Configuration
public class ExportReaderConfig {

	@Bean
	public JpaPagingItemReader<Account> exportReader(EntityManagerFactory entityManagerFactory) {
		return new JpaPagingItemReaderBuilder<Account>()
			.name("exportReader")
			.queryString("from br.com.charlesalves.sincronizacaoreceita.domain.Account")
			.entityManagerFactory(entityManagerFactory)
			.build();
	}
}
