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

}
