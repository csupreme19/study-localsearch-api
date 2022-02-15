package com.api.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import com.api.constants.RegexPatterns;

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
	public void checkNameDistance() {
		boolean flag = false;
		String kakaoName = "백화양곱창 6호";
		String naverName = "백화양<b>곱창</b>";
		naverName = naverName.replaceAll(RegexPatterns.HTML_TAG.getPattern(), "");
		int maxNameLen = Math.max(kakaoName.length(), naverName.length());
		int distance = AlgorithmUtils.levinshteinDistance(StringUtils.trimAllWhitespace(kakaoName), StringUtils.trimAllWhitespace(naverName));
		double percent = ((double)distance / (double)maxNameLen);
		if(percent <= 0.25) {
			log.info("consider same: {} == {} ({}%)", kakaoName, naverName, (int)(percent * 100));
			flag=true;
		}
		
		log.info("distance: {}", distance);
		assertTrue(flag);
	}
	
	@Test
	public void checkAddressDistance() {
		boolean flag = false;
		String kakaoRoadAddress = "경기 수원시 팔달구 장다리로 282";
		String naverRoadAddress = "경기도 수원시 팔달구 장다리로 282 의성빌딩";
		int maxAddressLen = Math.max(kakaoRoadAddress.length(), naverRoadAddress.length());
		int distance = AlgorithmUtils.levinshteinDistance(StringUtils.trimAllWhitespace(kakaoRoadAddress), StringUtils.trimAllWhitespace(naverRoadAddress));
		double percent = ((double)distance / (double)maxAddressLen);
		if(percent <= 0.25) {
			log.info("consider same: {} == {} ({}%)", kakaoRoadAddress, naverRoadAddress, (int)(percent * 100));
			flag=true;
		}
		
		log.info("distance: {}", distance);
		assertTrue(flag);
	}
}
