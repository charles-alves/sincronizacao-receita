package br.com.charlesalves.sincronizacaoreceita.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author gabriel_stabel<gabriel_stabel@sicredi.com.br>
 */
@Service
public class ReceitaService {

	private static List<String> tipos = Arrays.asList("A", "I", "B", "P");

	// Esta é a implementação interna do "servico" do banco central. Veja o código
	// fonte abaixo para ver os formatos esperados pelo Banco Central neste cenário.
	public boolean atualizarConta(String agencia, String conta, double saldo, String status)
			throws RuntimeException, InterruptedException {
        if (isAgenciaInvalida(agencia) || isContaInvalida(conta) || isStatusInvalido(status)) {
            return false;
        }

        simularDelay();
        simularExcesao();

        return true;
    }

    // Formato agencia: 0000
	private boolean isAgenciaInvalida(String agencia) {
		return agencia == null || agencia.length() != 4;
	}

    // Formato conta: 000000
	private boolean isContaInvalida(String conta) {
		return conta == null || conta.length() != 6;
	}

	private boolean isStatusInvalido(String status) {
		return status == null || !tipos.contains(status);
	}

    // Simula tempo de resposta do serviço (entre 1 e 5 segundos)
	private void simularDelay() throws InterruptedException {
		long wait = Math.round(Math.random() * 4000) + 1000;
        Thread.sleep(wait);
	}

    // Simula cenario de erro no serviço (0,1% de erro)
	private void simularExcesao() {
		long randomError = Math.round(Math.random() * 1000);
        if (randomError == 500) {
            throw new RuntimeException("Error");
        }
	}
}
