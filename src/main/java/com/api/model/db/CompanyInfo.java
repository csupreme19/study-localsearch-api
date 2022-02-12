package com.api.model.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Getter
@Table(name="company")
public class CompanyInfo {
	@Id
	private Long id;
	private String name;
	private Date createdAt;
	private Date modifiedAt;
	
	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="id", referencedColumnName="companyId")
	@JoinColumn(name="id")
	private ApiKeyInfo apiKey;
}
