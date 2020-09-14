package br.com.charlesalves.sincronizacaoreceita.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.batch.repeat.RepeatStatus;

import br.com.charlesalves.sincronizacaoreceita.domain.Account;
import br.com.charlesalves.sincronizacaoreceita.repositories.AccountDao;
import br.com.charlesalves.sincronizacaoreceita.services.ReceitaService;
import br.com.charlesalves.sincronizacaoreceita.steps.sincronization.SincronizationTasklet;

public class SincronizationStepTest {

	private SincronizationTasklet sincronizationTasklet;
	private AccountDao accountDao;
	private ReceitaService receitaService;

	@BeforeEach
	public void setup() {
		accountDao = mock(AccountDao.class);
		receitaService = mock(ReceitaService.class);

		sincronizationTasklet = new SincronizationTasklet(accountDao, receitaService);
	}

	@Test
	public void testSuccessfulSincronization() throws Exception {
		Account account = new Account("0000", "12225-6", 100.0, "A");
		when(accountDao.findAll()).thenReturn(Collections.singletonList(account));
		when(receitaService.atualizarConta(any(String.class), any(String.class), any(Double.class), any(String.class)))
				.thenReturn(true);

		RepeatStatus execute = sincronizationTasklet.execute(null, null);

		ArgumentCaptor<String> number = ArgumentCaptor.forClass(String.class);
		verify(receitaService, times(1)).atualizarConta(any(String.class), number.capture(), any(Double.class),
				any(String.class));

		assertThat(execute, is(equalTo(RepeatStatus.FINISHED)));
		assertThat(account.isSincronized(), is(true));
		assertThat(number.getValue(), is(equalTo("122256")));
	}

	@Test
	public void testServerSincronizationException() throws Exception {
		Account account = new Account("0000", "12225-6", 100.0, "A");
		when(accountDao.findAll()).thenReturn(Collections.singletonList(account));
		when(receitaService.atualizarConta(any(String.class), any(String.class), any(Double.class), any(String.class)))
			.thenThrow(RuntimeException.class)
			.thenReturn(true);

		RepeatStatus execute = sincronizationTasklet.execute(null, null);

		ArgumentCaptor<String> number = ArgumentCaptor.forClass(String.class);
		verify(receitaService, times(2)).atualizarConta(any(String.class), number.capture(), any(Double.class),
				any(String.class));

		assertThat(execute, is(equalTo(RepeatStatus.FINISHED)));
		assertThat(account.isSincronized(), is(true));
		assertThat(number.getValue(), is(equalTo("122256")));
	}
}
