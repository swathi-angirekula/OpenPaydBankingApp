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
@Table(name = "client")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends AuditModel {

//	public Client(Long id, String name, String surname, Address primaryAddress, Address secondaryAddress) {
//		this.id = id;
//		this.name = name;
//		this.surname = surname;
//		this.primaryAddress = primaryAddress;
//		this.secondaryAddress = secondaryAddress;
//	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String surname;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
	private Address primaryAddress;
	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
	private Address secondaryAddress;

	@Override
	public String toString() {
		return getName() + getSurname() + getPrimaryAddress() + getSecondaryAddress();
	}

//	/**
//	 * @return
//	 */
//	public Long getId() {
//		return id;
//	}
//
////	public void setId(Long id) {
////		this.id = id;
////	}
//	/**
//	 * @return
//	 */
//	public String getName() {
//		return name;
//	}
//
//	/**
//	 * @param name
//	 */
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	/**
//	 * @return
//	 */
//	public String getSurname() {
//		return surname;
//	}
//
//	/**
//	 * @param surname
//	 */
//	public void setSurname(String surname) {
//		this.surname = surname;
//	}
//
//	/**
//	 * @return
//	 */
//	public Address getPrimaryAddress() {
//		return primaryAddress;
//	}
//
//	/**
//	 * @param primaryAddress
//	 */
//	public void setPrimaryAddress(Address primaryAddress) {
//		this.primaryAddress = primaryAddress;
//	}
//
//	/**
//	 * @return
//	 */
//	public Address getSecondaryAddress() {
//		return secondaryAddress;
//	}
//
//	/**
//	 * @param secondaryAddress
//	 */
//	public void setSecondaryAddress(Address secondaryAddress) {
//		this.secondaryAddress = secondaryAddress;
//	}
//
}
