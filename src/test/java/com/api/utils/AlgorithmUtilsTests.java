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
}
