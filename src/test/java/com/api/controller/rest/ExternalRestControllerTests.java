package com.api.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.api.model.kakao.KakaoPlaceApiResponse;
import com.api.model.naver.NaverPlaceApiResponse;
import com.api.service.KakaoApiService;
import com.api.service.NaverApiService;

/**
 * 외부 API 호출 컨트롤러 테스트
 * 
 * @author csupreme19
 * @since 2022.02.13
 */
@WebFluxTest
public class ExternalRestControllerTests {
	
	@MockBean
	private KakaoApiService kakaoApiService;
	
	@MockBean
	private NaverApiService naverApiService;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void isKakaoPlaceApiSuccess() {
		webTestClient.get()
		.uri(uriBuilder -> uriBuilder.path("/external/kakao/search")
				.queryParam("query", "갈비집")
				.queryParam("size", 5)
				.build())
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(KakaoPlaceApiResponse.class);
	}
	
	@Test
	public void isNaverPlaceApiSuccess() {
		webTestClient.get()
		.uri(uriBuilder -> uriBuilder.path("/external/kakao/search")
				.queryParam("query", "갈비집")
				.queryParam("display", 5)
				.build())
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(NaverPlaceApiResponse.class);
	}
	
}
