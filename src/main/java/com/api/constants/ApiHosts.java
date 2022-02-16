package com.api.constants;

import lombok.Getter;

/**
 * API 호스트 정보
 * 
 * @author csupreme19
 * @since 2022.02.12
 */
@Getter
public enum ApiHosts {
	API_SERVER("http://localhost:8080")
	, KAKAO("https://dapi.kakao.com")
	, NAVER("https://openapi.naver.com");

	private String url;
	
	ApiHosts(String url) {
		this.url = url;
	}
}
