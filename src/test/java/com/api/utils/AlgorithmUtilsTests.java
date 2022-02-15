package com.api.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlgorithmUtilsTests {
	
	private final String[][] pairs = {{"abcd", "aecdb"}, {"google", "look at"}};
	private final int[] answer = {2, 5};
	
	@Test
	public void isAlgorithmPass() {
		for(int i=0; i<pairs.length; i++) {
			String[] pair = pairs[i];
			int distance = AlgorithmUtils.levinshteinDistance(pair[0], pair[1]);
			boolean flag = answer[i] == distance;
			log.info("{} ans:{} == distance:{}", flag, answer[i], distance);;
			assertTrue(flag);
		}
	}
	
	@Test
	public void printAlgorithm() {
		String kakaoRoadAddress = "경기 수원시 팔달구 장다리로 282";
		String naverRoadAddress = "경기도 수원시 팔달구 장다리로 282 의성빌딩";
		int maxAddressLen = Math.max(kakaoRoadAddress.length(), naverRoadAddress.length());
		int distance = AlgorithmUtils.levinshteinDistance("경기도 수원시 팔달구 장다리로 282 의성빌딩", "경기 수원시 팔달구 장다리로 282");
		double percent = ((double)distance / (double)maxAddressLen);
		if(percent <= 0.25) {
			log.info("consider same: {} == {} ({}%)", kakaoRoadAddress, naverRoadAddress, (int)(percent * 100));
		}
		
		log.info("distance: {}", distance);
		assertTrue(true);
	}
}
