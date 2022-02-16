package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.model.SearchCount;
import com.api.model.db.SearchHistoryInfo;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistoryInfo, Long> {
	@Query(value="SELECT s.keyword AS keyword, count(s.id) AS count "
			+ "FROM search_history s "
			+ "GROUP BY keyword "
			+ "ORDER BY count DESC"
			, nativeQuery=true)
	List<SearchCount> getAllSearchHistory();
}