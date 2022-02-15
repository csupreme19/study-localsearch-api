package com.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.model.db.CompanyInfo;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyInfo, Long> {
	CompanyInfo findOneById(Long id);
	CompanyInfo findOneByName(String name);
}
