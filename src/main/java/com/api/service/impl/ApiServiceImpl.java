package com.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.api.constants.RegexPatterns;
import com.api.model.PlaceApiRequest;
import com.api.model.PlaceApiResponse;
import com.api.model.kakao.KakaoPlaceApiRequest;
import com.api.model.kakao.KakaoPlaceApiResponse;
import com.api.model.naver.NaverPlaceApiRequest;
import com.api.model.naver.NaverPlaceApiResponse;
import com.api.service.ApiService;
import com.api.service.KakaoApiService;
import com.api.service.NaverApiService;

import reactor.core.publisher.Mono;

@Service
public class ApiServiceImpl implements ApiService {

	private static final String addressRegex = RegexPatterns.ADDRESS.getPattern();

	@Autowired
	KakaoApiService kakaoService;

	@Autowired
	NaverApiService naverService;


	@Override
	public PlaceApiResponse getPlaces(MultiValueMap<String, String> header, PlaceApiRequest request) {
		KakaoPlaceApiRequest kakaoRequest = new KakaoPlaceApiRequest();
		kakaoRequest.setQuery(request.getQuery());
		Mono<KakaoPlaceApiResponse> kakaoResponse = kakaoService.getKakaoPlaces(header, kakaoRequest);

		NaverPlaceApiRequest naverRequest = new NaverPlaceApiRequest();
		naverRequest.setQuery(request.getQuery());
		Mono<NaverPlaceApiResponse> naverResponse = naverService.getNaverPlaces(header, naverRequest);

		KakaoPlaceApiResponse kakaoResult = kakaoResponse.block();
		NaverPlaceApiResponse naverResult = naverResponse.block();

		// 도로명 주소를 기준으로 정렬한다.



		naverResponse.block();
		PlaceApiResponse response = new PlaceApiResponse();

		return response;
	}

}
