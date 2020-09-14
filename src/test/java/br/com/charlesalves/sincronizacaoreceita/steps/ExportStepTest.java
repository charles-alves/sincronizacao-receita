package br.com.charlesalves.sincronizacaoreceita.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.charlesalves.sincronizacaoreceita.util.TestUtils;

@SpringBootTest
@SpringBatchTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
@Sql(scripts = "/setup-database.sql")
@Sql(scripts = "/clean-up-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ExportStepTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@BeforeEach
	public void setup() {
		TestUtils.setup();
	}

	@AfterEach
	public void teardown() {
		TestUtils.teardown();
		jobRepositoryTestUtils.removeJobExecutions();
	}

	@Test
	public void testExportStep() throws IOException {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("export-file", TestUtils.defaultJobParameters());
		Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
		ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

		TestUtils.assertSuccessfulStep(actualStepExecutions, actualJobExitStatus);
		assertThat(new File(TestUtils.TEST_OUTPUT).exists(), is(true));
		assertThat(FileUtils.contentEquals(new File(TestUtils.TEST_OUTPUT), TestUtils.EXPECTED_RESULT_FILE), is(true));
	}
}
