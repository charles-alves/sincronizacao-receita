package br.com.charlesalves.sincronizacaoreceita.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
@SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 50)
public class Account implements Serializable {

	private static final long serialVersionUID = -1020244346703348312L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
	private Long id;

	@Column(name = "branch", nullable = false, length = 4)
	private String branch;

	@Column(name = "number", nullable = false, length = 7)
	private String number;

	@Column(name = "balance", nullable = false)
	private double balance;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "sincronized", nullable = false)
	private boolean sincronized;

	public Account() {
	}

	public Account(String branch, String number, double balance, String status) {
		this.branch = branch;
		this.number = number;
		this.balance = balance;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSincronized() {
		return sincronized;
	}

	public void setSincronized(boolean sincronized) {
		this.sincronized = sincronized;
	}
}
