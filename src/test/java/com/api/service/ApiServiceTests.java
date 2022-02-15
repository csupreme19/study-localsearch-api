package com.api.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.api.constants.RegexPatterns;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiServiceTests {

	private String[] testAddress = {"인천광역시 중구 은하수로 1"
			, "강원도 강릉시 강릉대로587번길 10-5"
			, "서울특별시 성동구 성수동1가 668-21"
			, "경기 수원시 팔달구 장다리로 282"
			, "경기도 수원시 팔달구 장다리로 282 의성빌딩"};
	
	private String addressPattern = RegexPatterns.ADDRESS.getPattern();
	
	@Test
	public void isAddress() {
		for(String address : testAddress) {
			boolean flag = Pattern.matches(addressPattern, address);
			log.debug("{} {} == {}", flag, addressPattern, address);
			assertTrue(flag);
		}
	}
	
	@Test
	public void isNotAddress() {
		String address = "http://csupreme19.github.io";
		boolean flag = Pattern.matches(addressPattern, address);
		log.debug("{} {} == {}", flag, addressPattern, address);
		assertFalse(flag);
	}
}
