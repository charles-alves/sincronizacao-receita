package br.com.charlesalves.sincronizacaoreceita.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.charlesalves.sincronizacaoreceita.domain.Account;

public interface AccountDao extends JpaRepository<Account, Long>{

}
