package com.api.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.api.model.SearchCount;
import com.api.model.db.SearchHistoryInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 검색 JpaRepository 테스트
 * 
 * @author csupreme19
 * @since 2022.02.16
 */
@Slf4j
@SpringBootTest
@Transactional
public class SearchHistoryRepositoryTests {
	
	@Autowired
	private SearchHistoryRepository searchHistoryRepository;
	
	@Test
	@Order(1)
	public void create() {
		SearchHistoryInfo result = searchHistoryRepository.save(SearchHistoryInfo
				.builder().keyword("testKeyword").build());
		log.info("create(): {}", result.toString());
		assertTrue(!ObjectUtils.isEmpty(result));
	}
	
	@Test
	@Order(2)
	public void read() {
		List<SearchHistoryInfo> result = searchHistoryRepository.findAll();
		assertTrue(!ObjectUtils.isEmpty(result));
	}
	
	@Test
	@Order(3)
	public void update() {
		SearchHistoryInfo item = searchHistoryRepository.getById((long) 1);
		item.updateInfo("updatedKeyword");
		SearchHistoryInfo updated = searchHistoryRepository.save(item);
		log.info("update(): {}", updated.toString());
		assertTrue(updated.getKeyword() == "updatedKeyword");
	}
	
	@Test
	@Order(4)
	public void delete() {
		boolean flag = false;
		searchHistoryRepository.deleteById((long)1);
		try {
			SearchHistoryInfo result = searchHistoryRepository.getById((long) 1);
		} catch(Exception e) {
			flag=true;
		}
		assertTrue(flag);
	}
	
	@Test
	public void getSearchHistory() {
		List<SearchCount> result = searchHistoryRepository.getAllSearchHistory();
		assertTrue(!ObjectUtils.isEmpty(result));
	}
}
