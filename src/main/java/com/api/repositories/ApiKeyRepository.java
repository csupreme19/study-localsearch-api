package com.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.api.model.db.ApiKeyInfo;

@RepositoryRestResource
public interface ApiKeyRepository extends JpaRepository<ApiKeyInfo, Long> {

	@RestResource
	ApiKeyInfo findOneById(Long id);
	
	@RestResource
	ApiKeyInfo findOneByCompanyId(Long companyId);
}
