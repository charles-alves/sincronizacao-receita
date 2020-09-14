package br.com.charlesalves.sincronizacaoreceita.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.charlesalves.sincronizacaoreceita.domain.Account;
import br.com.charlesalves.sincronizacaoreceita.steps.sincronization.SincronizationTasklet;

@Configuration
@EnableBatchProcessing
public class JobConfig {

	private JobBuilderFactory jobBuilderFactory;

	public JobConfig(JobBuilderFactory jobBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
	}

	@Bean
	public Step importFile(
		StepBuilderFactory stepBuilderFactory,
		FlatFileItemReader<Account> reader,
		JpaItemWriter<Account> writer
	) {
		return stepBuilderFactory.get("import-data")
			.<Account, Account>chunk(100)
			.reader(reader)
			.writer(writer)
			.build();
	}

	@Bean
	public Step sincronization(
		StepBuilderFactory stepBuilderFactory,
		SincronizationTasklet sincronizationTasklet
	) {
		return stepBuilderFactory.get("sincronization")
			.tasklet(sincronizationTasklet)
			.build();
	}

	@Bean
	public Step exportFile(
		StepBuilderFactory stepBuilderFactory,
		JpaPagingItemReader<Account> exportReader,
		FlatFileItemWriter<Account> exportWriter
	) {
		return stepBuilderFactory.get("export")
			.<Account, Account>chunk(100)
			.reader(exportReader)
			.writer(exportWriter)
			.build();
	}

	@Bean
	public Job job(
		@Qualifier("importFile") Step importFile,
		@Qualifier("sincronization") Step sincronization,
		@Qualifier("exportFile") Step exportFile
	) {
		return jobBuilderFactory.get("sincronization")
			.start(importFile)
			.next(sincronization)
			.next(exportFile)
			.build();
	}
}
