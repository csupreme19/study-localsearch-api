package com.api.service;

import org.springframework.stereotype.Service;

import com.api.model.kakao.KakaoResponse;

/**
 * 카카오 로컬 API 호출 서비스
 * 
 * @author csupreme19
 * @since 2022.02.12
 */
@Service
public interface KakaoApiService {
	
	/**
	 * 장소 검색 API 호출
	 */
	public KakaoResponse getPlaces();
}
