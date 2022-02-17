package com.api.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.kakao.KakaoPlaceApiRequest;
import com.api.model.kakao.KakaoPlaceApiResponse;
import com.api.model.naver.NaverPlaceApiRequest;
import com.api.model.naver.NaverPlaceApiResponse;
import com.api.service.KakaoApiService;
import com.api.service.NaverApiService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 외부 장소 검색 REST API 서버
 * WebFlux 사용 non-blocking
 * @author csupreme19
 * @since 2022.02.12
 *
 */
@Slf4j
@RestController
@RequestMapping("/external")
public class ExternalRestController {

	@Autowired
	KakaoApiService kakaoService;
	
	@Autowired
	NaverApiService naverService;
	
	/**
	 * 카카오 검색 API
	 */
	@GetMapping(path = "/kakao/places")
	public Mono<?> getKakaoPlaces(@RequestHeader MultiValueMap<String, String> header, @Valid KakaoPlaceApiRequest request){
		Mono<KakaoPlaceApiResponse> response = kakaoService.getKakaoPlaces(header, request);
		return response;
	}
	
	/**
	 * 네이버 검색 API
	 */
	@GetMapping(path = "/naver/places")
	public Mono<?> getNaverPlaces(@RequestHeader MultiValueMap<String, String> header, @Valid NaverPlaceApiRequest request){
		Mono<NaverPlaceApiResponse> response = naverService.getNaverPlaces(header, request);
		return response;
	}
	
}
