package com.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.api.model.db.CompanyInfo;

@RepositoryRestResource
public interface CompanyRepository extends JpaRepository<CompanyInfo, Long> {

	@RestResource
	CompanyInfo findOneById(Long id);
	
	@RestResource
	CompanyInfo findOneByName(String name);
}
