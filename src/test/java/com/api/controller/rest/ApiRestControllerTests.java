package com.api.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.api.model.PlaceApiResponse;
import com.api.service.ApiService;
import com.api.service.KakaoApiService;
import com.api.service.NaverApiService;

/**
 * API 호출 컨트롤러 테스트
 * 
 * @author csupreme19
 * @since 2022.02.13
 */
@WebFluxTest
public class ApiRestControllerTests {
	
	@MockBean
	private ApiService apiService;
	
	@MockBean
	private KakaoApiService kakaoApiService;
	
	@MockBean
	private NaverApiService naverApiService;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void isApiNotFound() {
		webTestClient.get()
		.uri(uriBuilder -> uriBuilder.path("/notexists")
				.queryParam("query", "갈비집")
				.queryParam("size", 5)
				.build())
		.exchange()
		.expectStatus().isNotFound();
	}
	
	@Test
	public void isPlaceApiBadRequest() {
		webTestClient.get()
		.uri(uriBuilder -> uriBuilder.path("/api/v1/places")
				.build())
		.exchange()
		.expectStatus().isBadRequest();
	}
	
	@Test
	public void isPlaceApiSuccess() {
		webTestClient.get()
		.uri(uriBuilder -> uriBuilder.path("/api/v1/places")
				.queryParam("query", "곱창")
				.build())
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(PlaceApiResponse.class);
	}
	
	@Test
	public void isTrendApiSuccess() {
		webTestClient.get()
		.uri(uriBuilder -> uriBuilder.path("/api/v1/trends")
				.build())
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(PlaceApiResponse.class);
	}
	
}
