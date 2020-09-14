package br.com.charlesalves.sincronizacaoreceita.steps.importdata;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import br.com.charlesalves.sincronizacaoreceita.domain.Account;
import br.com.charlesalves.sincronizacaoreceita.util.NumberUtil;

@Configuration
public class ImportReaderConfig {

	@Bean
	public FlatFileItemReader<Account> importReader(
		@Value("${file.source}") String resourcePath
	) {
		Resource resource = new FileSystemResource(resourcePath);

		return new FlatFileItemReaderBuilder<Account>()
			.name("accountFileReader")
			.strict(false)
			.linesToSkip(1)
			.resource(resource)
			.encoding("UTF-8")
			.targetType(Account.class)
			.lineMapper(this::lineMapper)
			.build();
	}

	private Account lineMapper(String line, int lineNumber) {
		String[] data = line.split(";");
		double balance = NumberUtil.toDouble(data[2]);
		return new Account(data[0], data[1], balance, data[3]);
	}
}
