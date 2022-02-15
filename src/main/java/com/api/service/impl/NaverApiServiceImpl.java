package com.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import com.api.constants.ApiEndpoints;
import com.api.constants.ApiHosts;
import com.api.model.db.ApiKeyInfo;
import com.api.model.db.CompanyInfo;
import com.api.model.naver.NaverPlaceApiRequest;
import com.api.model.naver.NaverPlaceApiResponse;
import com.api.repositories.CompanyRepository;
import com.api.service.NaverApiService;
import com.api.utils.ObjectMapperUtil;
import com.api.utils.WebClientUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class NaverApiServiceImpl implements NaverApiService {

	@Autowired
	CompanyRepository companyRepo;
	
	@Transactional
	@Override
	public Mono<NaverPlaceApiResponse> getNaverPlaces(MultiValueMap<String, String> header, NaverPlaceApiRequest request) {
		String url = ApiHosts.NAVER.getUrl() + ApiEndpoints.NAVER_LOCAL_SEARCH;
		CompanyInfo companyInfo = companyRepo.findOneByName(ApiHosts.NAVER.name().toLowerCase());
		ApiKeyInfo apiKeyInfo = companyInfo.getApiKey();
		
		if(!ObjectUtils.isEmpty(header)) {
			header = new LinkedMultiValueMap<>();
		}
		header.add("X-Naver-Client-Id", apiKeyInfo.getClientId());
		header.add("X-Naver-Client-Secret", apiKeyInfo.getClientSecret());
		MultiValueMap<String, String> params = ObjectMapperUtil.parseMap(request);
		Mono<NaverPlaceApiResponse> response = WebClientUtil.get(url, header, params)
				.bodyToMono(NaverPlaceApiResponse.class);
		return response;
	}

}
