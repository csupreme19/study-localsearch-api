package com.api.model.db;

import java.time.LocalDateTime;
import java.util.Date;

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
@Table(name="company")
public class CompanyInfo {
	@Id
	private Long id;
	private String name;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	private ApiKeyInfo apiKey;
}
