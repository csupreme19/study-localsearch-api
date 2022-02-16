package com.api.model.db;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;

@Entity
@Getter
@Table(name="api_key")
public class ApiKeyInfo {
	@Id
	private Long id;
	private Long companyId;
	private String key;
	private String clientId;
	private String clientSecret;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
	
//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="company_id", referencedColumnName="id")
//	private CompanyInfo company;
}
