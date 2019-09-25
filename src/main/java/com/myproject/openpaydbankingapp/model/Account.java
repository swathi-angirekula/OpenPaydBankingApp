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
}
