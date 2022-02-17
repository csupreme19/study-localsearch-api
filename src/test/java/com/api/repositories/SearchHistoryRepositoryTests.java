package com.api.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ObjectUtils;

import com.api.model.db.SearchHistoryInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 검색 JpaRepository 테스트
 * 
 * @author csupreme19
 * @since 2022.02.16
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SearchHistoryRepositoryTests {
	
	private static final int nThreads = 2000;
	
	private static final ExecutorService service = Executors.newFixedThreadPool(nThreads);
	
	@Autowired
	private SearchHistoryRepository searchHistoryRepository;
	
	@Test
	@Order(1)
	public void create() {
		SearchHistoryInfo result = searchHistoryRepository.save(SearchHistoryInfo.builder()
				.keyword("테스트").build());
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
		SearchHistoryInfo result = searchHistoryRepository.save(SearchHistoryInfo.builder()
				.keyword("테스트")
				.count(0L)
				.build());
		result.setCount(result.getCount()+1);
		SearchHistoryInfo item = searchHistoryRepository.findOneByKeyword("테스트");
		log.info("update(): {}", item.toString());
		assertTrue(item.getCount()==1);
	}
	
	@Test
	@Order(4)
	public void delete() {
		SearchHistoryInfo result = searchHistoryRepository.save(SearchHistoryInfo.builder()
				.keyword("테스트").build());
		int res = searchHistoryRepository.deleteByKeyword("테스트");
		log.info("delete(): {}", res);
		assertTrue(res==1);
	}
	
	@Test
	public void isConcurrencyTestSuccess() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(nThreads);
		String query = "test";
		SearchHistoryInfo item = searchHistoryRepository.save(SearchHistoryInfo.builder()
				.keyword(query)
				.count(0L)
				.build());
		
		for(int i=0; i<nThreads; i++) {
			service.execute(() -> {
				item.setCount(item.getCount()+1);
				latch.countDown();
			});
		}
		
		latch.await();
		
		SearchHistoryInfo result = searchHistoryRepository.findOneByKeyword(query);
		log.info("success concurrency threads, count: {}, {}", nThreads, result.getCount());
		
		assertTrue(result.getCount() == nThreads);
	}
	
	@Test
	public void isConcurrencyTestFail() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(nThreads);
		String query = "test";
		SearchHistoryInfo item = searchHistoryRepository.save(SearchHistoryInfo.builder()
				.keyword(query)
				.count(0L)
				.build());
		
		for(int i=0; i<nThreads; i++) {
			service.execute(() -> {
				item.setCount(item.getCount()+1);
				latch.countDown();
			});
		}
		
		latch.await();
		
		SearchHistoryInfo result = searchHistoryRepository.findOneByKeywordOrderByKeyword(query);
		log.info("fail concurrency threads, count: {}, {}", nThreads, result.getCount());
		
		assertTrue(result.getCount() != nThreads);
	}
}
