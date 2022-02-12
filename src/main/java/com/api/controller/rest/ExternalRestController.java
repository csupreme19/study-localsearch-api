package com.api.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.KakaoPlaceApiRequest;
import com.api.model.kakao.KakaoResponse;
import com.api.service.KakaoApiService;

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
@RequestMapping("/api/v1")
public class ExternalRestController {

	@Autowired
	KakaoApiService service;
	
	/**
	 * 카카오 검색 API
	 */
	@GetMapping(path = "/kakao/search")
	public Mono<KakaoResponse> getKakaoPlaces(@Valid @ModelAttribute KakaoPlaceApiRequest request){
		Mono<KakaoResponse> response = service.getKakaoPlaces(request);
		return response;
	}
	
}
