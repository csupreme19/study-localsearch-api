package com.api.service;

import org.springframework.stereotype.Service;

import com.api.model.PlaceApiResponse;

/**
 * 장소 검색 API 서비스
 * 
 * @author csupreme19
 * @since 2022.02.12
 */
@Service
public interface ApiService {
	
	/**
	 * 장소 검색 API 호출
	 */
	public PlaceApiResponse getPlaces();

}
