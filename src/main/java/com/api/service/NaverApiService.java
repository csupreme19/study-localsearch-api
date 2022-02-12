package com.api.service;

import org.springframework.stereotype.Service;

import com.api.model.naver.NaverPlaceApiRequest;
import com.api.model.naver.NaverPlaceApiResponse;

import reactor.core.publisher.Mono;

/**
 * 네이버 장소 API 호출 서비스
 * 
 * @author csupreme19
 * @since 2022.02.12
 */
@Service
public interface NaverApiService {
	
	/**
	 * 장소 검색 API 비동기 호출
	 */
	public Mono<NaverPlaceApiResponse> getNaverPlaces(NaverPlaceApiRequest request);
}
