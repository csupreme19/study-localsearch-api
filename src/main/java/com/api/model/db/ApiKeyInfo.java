package com.api.model.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Getter
@Table(name="api_key")
public class ApiKeyInfo {
	@Id
	private Long id;
	private Long companyId;
	private String key;
	private String client_id;
	private String client_secret;
	private Date createdAt;
	private Date modifiedAt;
	
//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="company_id", referencedColumnName="id")
//	private CompanyInfo company;
}
