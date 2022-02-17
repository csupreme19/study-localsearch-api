package com.api.constants;

/**
 * API Endpoints 정보
 * 
 * @author csupreme19
 * @since 2022.02.12
 */
public interface ApiEndpoints {
	String KAKAO_SEARCH = "/external/kakao/places";
	String NAVER_SEARCH = "/external/naver/places";
	
	String OPEN_API_KAKAO_SEARCH = "/v2/local/search/keyword.json";
	String OPEN_API_NAVER_SEARCH = "/v1/search/local.json";
}
