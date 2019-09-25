package com.myproject.openpaydbankingapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "address")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	@Setter
	private Long id;
	@Getter
	@Setter
	private String addressLine1;
	@Getter
	@Setter
	private String addressLine2;
	@Getter
	@Setter
	private String city;
	@Getter
	@Setter
	private String country;

	@Override
	public String toString() {
		return getAddressLine1() + getAddressLine1() + getCity() + getCountry();
	}

}
