package com.myproject.openpaydbankingapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Swathi Angirekula
 *
 */
@JsonIgnoreProperties(value = { "dateCreated" }, allowGetters = true)
@Entity
@Table(name = "transaction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends AuditModel {

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Getter
	@Setter
	private Long debitAccountId;
	/**
	 * 
	 */
	@Getter
	@Setter
	private Long creditAccountId;

	/**
	 * 
	 */
	@Getter
	@Setter
	private Double amount;
	/**
	 * 
	 */
	@Getter
	@Setter
	private String message;

	@Override
	public String toString() {
		return getDebitAccountId() + getCreditAccountId() + getAmount() + getMessage();
	}

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
//	public Long getDebitAccountId() {
//		return debitAccountId;
//	}
//
//	/**
//	 * @param debitAccountId
//	 */
//	public void setDebitAccountId(Long debitAccountId) {
//		this.debitAccountId = debitAccountId;
//	}
//
//	/**
//	 * @return
//	 */
//	public Long getCreditAccountId() {
//		return creditAccountId;
//	}
//
//	/**
//	 * @param creditAccountId
//	 */
//	public void setCreditAccountId(Long creditAccountId) {
//		this.creditAccountId = creditAccountId;
//	}
//
//	/**
//	 * @return
//	 */
//	public Double getAmount() {
//		return amount;
//	}
//
//	/**
//	 * @param amount
//	 */
//	public void setAmount(Double amount) {
//		this.amount = amount;
//	}
//
//	/**
//	 * @return
//	 */
//	public String getMessage() {
//		return message;
//	}
//
//	/**
//	 * @param message
//	 */
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
}
