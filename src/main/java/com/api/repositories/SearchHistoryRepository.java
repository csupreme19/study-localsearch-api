package com.api.repositories;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.api.model.db.SearchHistoryInfo;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistoryInfo, String> {
	
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="1000")})
	SearchHistoryInfo findOneByKeyword(String keyword);
	
	SearchHistoryInfo findOneByKeywordOrderByKeyword(String keyword);
	
	int deleteByKeyword(String keyword);
	
}