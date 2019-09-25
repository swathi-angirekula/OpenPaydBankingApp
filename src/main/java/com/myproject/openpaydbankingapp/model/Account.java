package com.myproject.openpaydbankingapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Swathi Angirekula
 *
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account extends AuditModel {

	@Getter
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	@Getter
	@Setter
	private Double balance;
	@Getter
	@Setter
	private Type accountType;
	@Getter
	@Setter
	private BalanceStatus balanceStatus;
	@OneToOne(cascade = CascadeType.ALL)
	@Getter
	@Setter
	private Client client;

	public static enum Type {
		CURRENT, SAVINGS
	}

	public static enum BalanceStatus {
		DR, CR
	}

	@Override
	public String toString() {
		return getAccountType().toString() + getBalance() + getBalanceStatus() + getClient();
	}
//	public Account(Long id, Double balance, Type accountType, BalanceStatus balanceStatus, Client client) {
//		this.id = id;
//		this.balance = balance;
//		this.accountType = accountType;
//		this.balanceStatus = balanceStatus;
//		this.client = client;
//	}
//	
//	/**
//	 * @return
//	 */
//	public Type getAccountType() {
//		return accountType;
//	}
//
//	/**
//	 * @param accountType
//	 */
//	public void setAccountType(Type accountType) {
//		this.accountType = accountType;
//	}
//
//	/**
//	 * @return
//	 */
//	public BalanceStatus getBalanceStatus() {
//		return balanceStatus;
//	}
//
//	/**
//	 * @param balanceStatus
//	 */
//	public void setBalanceStatus(BalanceStatus balanceStatus) {
//		this.balanceStatus = balanceStatus;
//	}
//
//	/**
//	 * @return
//	 */
//	public Long getId() {
//		return id;
//	}
////	public void setId(Long id) {
////		this.id = id;
////	}
//
//	/**
//	 * @return
//	 */
//	public Double getBalance() {
//		return balance;
//	}
//
//	/**
//	 * @param balance
//	 */
//	public void setBalance(Double balance) {
//		this.balance = balance;
//	}
//
//	/**
//	 * @return
//	 */
//	public Client getClient() {
//		return client;
//	}
//
//	/**
//	 * @param client
//	 */
//	public void setClient(Client client) {
//		this.client = client;
//	}

}
