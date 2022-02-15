package com.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.model.db.ApiKeyInfo;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyInfo, Long> {
	ApiKeyInfo findOneById(Long id);
	ApiKeyInfo findOneByCompanyId(Long companyId);
}
