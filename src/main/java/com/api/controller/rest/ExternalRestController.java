package com.api.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
 * 
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
	@GetMapping(path = "/kakao/search")
	public Mono<KakaoPlaceApiResponse> getKakaoPlaces(@Valid @ModelAttribute KakaoPlaceApiRequest request){
		Mono<KakaoPlaceApiResponse> response = kakaoService.getKakaoPlaces(request);
		return response;
	}
	
	/**
	 * 네이버 검색 API
	 */
	@GetMapping(path = "/naver/search")
	public Mono<NaverPlaceApiResponse> getNaverPlaces(@Valid @ModelAttribute NaverPlaceApiRequest request){
		Mono<NaverPlaceApiResponse> response = naverService.getNaverPlaces(request);
		return response;
	}
	
}
