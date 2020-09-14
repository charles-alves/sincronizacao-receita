package br.com.charlesalves.sincronizacaoreceita.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;

public class TestUtils {

	private static final String TEST_BASE_DIR = "C:\\Users\\charles.alves\\test";
	public static final String TEST_INPUT = TEST_BASE_DIR + "\\data\\in\\accounts.csv";
	public static final String TEST_OUTPUT = TEST_BASE_DIR + "\\data\\out\\accounts.done.csv";

	public static final File EXPECTED_RESULT_FILE = new File(ClassLoader.getSystemResource("accounts.done.csv").getPath());

	public static void setup() {
		try {
			IOUtils.createParentDir(TEST_INPUT);

			Path source = Paths.get(ClassLoader.getSystemResource("accounts.csv").toURI());
			Path target = Paths.get(TEST_INPUT);

			IOUtils.copy(source, target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void teardown() {
		try {
			FileUtils.deleteDirectory(new File(TEST_BASE_DIR));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static JobParameters defaultJobParameters() {
		JobParametersBuilder paramsBuilder = new JobParametersBuilder();
		paramsBuilder.addString("fileSource", TEST_INPUT);
		paramsBuilder.addString("fileOutput", TEST_OUTPUT);
		return paramsBuilder.toJobParameters();
	}

	public static void assertSuccessfulStep(Collection<StepExecution> actualStepExecutions, ExitStatus actualJobExitStatus) {
		assertThat(actualStepExecutions.size(), is(1));
	    assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
	}

}
