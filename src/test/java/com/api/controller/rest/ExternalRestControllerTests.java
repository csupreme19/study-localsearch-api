package com.api.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.api.model.kakao.KakaoResponse;
import com.api.service.KakaoApiService;

/**
 * 외부 API 호출 컨트롤러 테스트
 * 
 * @author csupreme19
 * @since 2022.02.13
 */
@WebFluxTest
public class ExternalRestControllerTests {
	
	@MockBean
	private KakaoApiService kakoApiService;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void isKakaoPlaceApiSuccess() {
		webTestClient.get()
		.uri(uriBuilder -> uriBuilder.path("/api/v1/kakao/search")
				.queryParam("query", "갈비집")
				.queryParam("size", 5)
				.build())
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(KakaoResponse.class);
	}
	
}
