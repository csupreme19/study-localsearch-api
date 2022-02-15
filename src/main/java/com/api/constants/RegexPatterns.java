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
	ADDRESS("(([가-힣]+(시|도))( +|)[가-힣]+(시|군|구))( +|)(([가-힣\\d\\-]+(읍|동|가|로|길))( +|)[\\d\\-]+)");

	private String pattern;
	
	RegexPatterns(String pattern) {
		this.pattern = pattern;
	}
}
