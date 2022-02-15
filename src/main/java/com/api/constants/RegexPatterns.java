package com.api.constants;

import lombok.Getter;

/**
 * 정규표현식 상수
 * 
 * @author csupreme19
 *
 */
@Getter
public enum RegexPatterns {
	ADDRESS("([가-힣 ]+)+(([가-힣\\d\\-\\.]+(읍|동|가|로|길)\\s)+[\\d\\-]+( +|)(|[가-힣]+)+)");

	private String pattern;
	
	RegexPatterns(String pattern) {
		this.pattern = pattern;
	}
}
