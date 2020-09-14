package br.com.charlesalves.sincronizacaoreceita.steps.export;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import br.com.charlesalves.sincronizacaoreceita.domain.Account;

@Configuration
public class ExportWriterConfig {

	@Bean
	public FlatFileItemWriter<Account> exportWriter(@Value("${file.output}") String outputFile) {
		FileSystemResource output = new FileSystemResource(outputFile);

		return new FlatFileItemWriterBuilder<Account>()
			.name("exportWriter")
			.resource(output)
			.headerCallback(writer -> writer.write("agencia;conta;saldo;status;sincronizado"))
			.delimited()
				.delimiter(";")
				.names("branch", "number", "balance", "status", "sincronized")
			.build();
	}
}
