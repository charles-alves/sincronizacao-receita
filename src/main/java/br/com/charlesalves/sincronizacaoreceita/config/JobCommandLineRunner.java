package br.com.charlesalves.sincronizacaoreceita.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class JobCommandLineRunner implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job sincronizationJob;

	@Value("${file.source}")
	private String defaultResourcePath;

	@Value("${file.output}")
	private String outputFile;

	@Override
	public void run(String... args) throws Exception {
		JobParametersBuilder paramsBuilder = new JobParametersBuilder();
		paramsBuilder.addString("fileSource", defaultResourcePath);
		paramsBuilder.addString("fileOutput", outputFile);
		jobLauncher.run(sincronizationJob, paramsBuilder.toJobParameters());
	}
}
