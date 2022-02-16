package com.api.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.api.model.PlaceApiRequest;
import com.api.model.PlaceApiResponse;
import com.api.model.TrendApiResponse;

/**
 * 장소 검색 API 서비스
 * 
 * @author csupreme19
 * @since 2022.02.12
 */
@Service
public interface ApiService {
	
	/**
	 * 장소 검색 API
	 */
	public PlaceApiResponse getPlaces(MultiValueMap<String, String> header, PlaceApiRequest request);

	
	/**
	 * 인기 검색 키워드 API (쿼리 이용 로직 처리)
	 */
	public TrendApiResponse getTrendsQuery(MultiValueMap<String, String> header);
	
	/**
	 * 인기 검색 키워드 API (서버에서 로직 처리)
	 */
	public TrendApiResponse getTrends(MultiValueMap<String, String> header);
}
