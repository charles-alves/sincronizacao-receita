package br.com.charlesalves.sincronizacaoreceita.steps.sincronization;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import br.com.charlesalves.sincronizacaoreceita.domain.Account;
import br.com.charlesalves.sincronizacaoreceita.repositories.AccountDao;
import br.com.charlesalves.sincronizacaoreceita.services.ReceitaService;

@Component
public class SincronizationTasklet implements Tasklet {

	private AccountDao accountDao;
	private ReceitaService receitaService;

	public SincronizationTasklet(AccountDao accountDao, ReceitaService receitaService) {
		this.accountDao = accountDao;
		this.receitaService = receitaService;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<Account> accounts = accountDao.findAll();

		for (Account account : accounts) {
			sincronizarReceita(account);
		}

		return RepeatStatus.FINISHED;
	}

	private void sincronizarReceita(Account account) {
		try {
			String branch = sanitize(account.getBranch());
			String number = sanitize(account.getNumber());

			boolean sincronizationStatus = receitaService.atualizarConta(branch, number, account.getBalance(),
					account.getStatus());

			account.setSincronized(sincronizationStatus);
		} catch (Exception e) {
			sincronizarReceita(account);
		}
	}

	private String sanitize(String str) {
		return str.replaceAll("[^0-9.]", "");
	}
}
