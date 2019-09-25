package com.myproject.openpaydbankingapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

/**
 * @author Swathi Angirekula
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdDate" }, allowGetters = true)
public abstract class AuditModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdDate", nullable = false, updatable = false)
	@CreatedDate
	@Getter
	private Date createdDate;
//
//	/**
//	 * @return
//	 */
//	public Date getCreatedDate() {
//		return createdDate;
//	}

}
