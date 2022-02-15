package com.api.controller.rest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 장소 검색 REST API 서버
 * 
 * @author csupreme19
 * @since 2022.02.12
 *
 */
@RequestMapping("/api/v1")
@RestController
public class ApiRestController {

	/**
	 * 장소 검색 API
	 * 
	 * 카카오 검색 API, 네이버 검색 API 호출하여 각각 5개의 장소를 가져와 10개로 보여준다.
	 */
	@GetMapping(path = "/place")
	public ResponseEntity<?> getPlaces(){
		return null;
	}
	
	// 테스트
	@GetMapping(path = "/test/{id}")
	public ResponseEntity<?> getTest(@PathVariable String id, @RequestParam String param1, @RequestParam String param2) {	
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(path = "/test/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> postTest(@PathVariable String id, @RequestBody Map<String, String> body) {
		return new ResponseEntity<>("{\"key\": \"value\"}", HttpStatus.OK);
	}
	
}
