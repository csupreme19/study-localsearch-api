package com.api.service;

import org.springframework.stereotype.Service;

import com.api.model.naver.NaverResponse;

/**
 * 카카오 장소 API 호출 서비스
 * 
 * @author csupreme19
 * @since 2022.02.12
 */
@Service
public interface NaverApiService {
	
	/**
	 * 장소 검색 API 호출
	 */
	public NaverResponse getPlaces();
}
