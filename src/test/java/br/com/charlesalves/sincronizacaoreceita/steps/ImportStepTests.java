package br.com.charlesalves.sincronizacaoreceita.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;

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

import br.com.charlesalves.sincronizacaoreceita.repositories.AccountDao;
import br.com.charlesalves.sincronizacaoreceita.util.TestUtils;

@SpringBootTest
@SpringBatchTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
class ImportStepTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@Autowired
	private AccountDao accountDao;

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
	public void testImportStep() {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("import-data", TestUtils.defaultJobParameters());
	    Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
	    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

	    TestUtils.assertSuccessfulStep(actualStepExecutions, actualJobExitStatus);
	    assertThat(accountDao.count(), is(equalTo(5L)));
	}
}
