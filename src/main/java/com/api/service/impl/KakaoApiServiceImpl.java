package com.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.api.constants.ApiEndpoints;
import com.api.constants.ApiHosts;
import com.api.model.KakaoPlaceApiRequest;
import com.api.model.db.ApiKeyInfo;
import com.api.model.db.CompanyInfo;
import com.api.model.kakao.KakaoResponse;
import com.api.repositories.CompanyRepository;
import com.api.service.KakaoApiService;
import com.api.utils.ObjectMapperUtil;
import com.api.utils.WebClientUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KakaoApiServiceImpl implements KakaoApiService {
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Transactional
	@Override
	public Mono<KakaoResponse> getKakaoPlaces(KakaoPlaceApiRequest request) {
		String url = ApiHosts.KAKAO.getUrl() + ApiEndpoints.KAKAO_LOCAL_SEARCH;
		CompanyInfo companyInfo = companyRepo.findOneByName(ApiHosts.KAKAO.name().toLowerCase());
		ApiKeyInfo apiKeyInfo = companyInfo.getApiKey();
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.AUTHORIZATION, "KakaoAK " + apiKeyInfo.getKey());
		MultiValueMap<String, String> params = ObjectMapperUtil.parseMap(request);
		Mono<KakaoResponse> response = WebClientUtil.get(url, headers, params)
				.bodyToMono(KakaoResponse.class);
		return response;
	}

}
