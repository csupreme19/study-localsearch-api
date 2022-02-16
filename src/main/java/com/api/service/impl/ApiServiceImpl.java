package com.api.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.api.constants.ApiEndpoints;
import com.api.constants.ApiHosts;
import com.api.constants.RegexPatterns;
import com.api.model.PlaceApiRequest;
import com.api.model.PlaceApiResponse;
import com.api.model.PlaceInfo;
import com.api.model.SearchCount;
import com.api.model.TrendApiResponse;
import com.api.model.TrendInfo;
import com.api.model.db.SearchHistoryInfo;
import com.api.model.kakao.DocumentInfo;
import com.api.model.kakao.KakaoPlaceApiRequest;
import com.api.model.kakao.KakaoPlaceApiResponse;
import com.api.model.naver.ItemInfo;
import com.api.model.naver.NaverPlaceApiRequest;
import com.api.model.naver.NaverPlaceApiResponse;
import com.api.repositories.SearchHistoryRepository;
import com.api.service.ApiService;
import com.api.service.KakaoApiService;
import com.api.service.NaverApiService;
import com.api.utils.AlgorithmUtils;
import com.api.utils.ObjectMapperUtil;
import com.api.utils.WebClientUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ApiServiceImpl implements ApiService {

	private static final String addressRegex = RegexPatterns.ADDRESS.getPattern();
	private static final String htmlTagRegex = RegexPatterns.HTML_TAG.getPattern();

	@Autowired
	SearchHistoryRepository searchHistoryRepository;
	
	@Autowired
	KakaoApiService kakaoService;

	@Autowired
	NaverApiService naverService;

	
	@Transactional
	@Override
	public PlaceApiResponse getPlaces(MultiValueMap<String, String> header, PlaceApiRequest request) {
		KakaoPlaceApiRequest kakaoRequest = new KakaoPlaceApiRequest();
		String query = request.getQuery();
		kakaoRequest.setQuery(query);
		kakaoRequest.setSize("5");
		MultiValueMap<String, String> kakaoParams = ObjectMapperUtil.parseMap(kakaoRequest);
		Mono<KakaoPlaceApiResponse> kakaoResponse = WebClientUtil.get(ApiHosts.API_SERVER.getUrl()+ApiEndpoints.KAKAO_SEARCH, header, kakaoParams).bodyToMono(KakaoPlaceApiResponse.class);;

		NaverPlaceApiRequest naverRequest = new NaverPlaceApiRequest();
		naverRequest.setQuery(query);
		naverRequest.setDisplay(5);
		MultiValueMap<String, String> naverParams = ObjectMapperUtil.parseMap(naverRequest);
		Mono<NaverPlaceApiResponse> naverResponse = WebClientUtil.get(ApiHosts.API_SERVER.getUrl()+ApiEndpoints.NAVER_SEARCH, header, naverParams).bodyToMono(NaverPlaceApiResponse.class);;

		KakaoPlaceApiResponse kakaoResult = kakaoResponse.block();
		NaverPlaceApiResponse naverResult = naverResponse.block();

		List<PlaceInfo> places = new ArrayList<>();
		Set<String> kakaoSet = new HashSet<>();
		Set<String> naverSet = new HashSet<>();

		for(DocumentInfo kakaoItem : kakaoResult.getDocuments()) {
			String kakaoAddress = kakaoItem.getAddressName();
			String kakaoRoadAddress = kakaoItem.getRoadAddressName();
			String kakaoName = kakaoItem.getPlaceName();

			if(!Pattern.matches(addressRegex, kakaoAddress) && !Pattern.matches(addressRegex, kakaoRoadAddress)) continue;
			if(!Pattern.matches(addressRegex, kakaoAddress)) kakaoAddress = "";
			if(!Pattern.matches(addressRegex, kakaoRoadAddress)) kakaoRoadAddress = "";

			for(ItemInfo naverItem : naverResult.getItems()) {
				String naverAddress = naverItem.getAddress();
				String naverRoadAddress = naverItem.getRoadAddress();
				String naverName = naverItem.getTitle();
				// HTML 태그 제거
				if(!ObjectUtils.isEmpty(naverName)) naverName = naverName.replaceAll(htmlTagRegex, "");

				if(!Pattern.matches(addressRegex, naverAddress) && !Pattern.matches(addressRegex, naverRoadAddress)) continue;
				if(!Pattern.matches(addressRegex, naverAddress)) naverAddress = "";
				if(!Pattern.matches(addressRegex, naverRoadAddress)) naverRoadAddress = "";
				
				int maxNameLen =  Math.max(kakaoName.length(), naverName.length());
				int maxAddressLen =  Math.max(kakaoAddress.length(), naverAddress.length());
				int maxRoadAddressLen =  Math.max(kakaoRoadAddress.length(), naverRoadAddress.length());

				int nameDistance = maxNameLen;
				int addressDistance = maxAddressLen;
				int roadAddressDistance = maxRoadAddressLen;

				// 이름 비교
				if(!ObjectUtils.isEmpty(kakaoName) && !ObjectUtils.isEmpty(naverName)) {
					nameDistance = AlgorithmUtils.levinshteinDistance(StringUtils.trimAllWhitespace(kakaoName), StringUtils.trimAllWhitespace(naverName));
				}
				
				// 동주소 비교
				if(!ObjectUtils.isEmpty(kakaoAddress) && !ObjectUtils.isEmpty(naverAddress)) {
					addressDistance = AlgorithmUtils.levinshteinDistance(StringUtils.trimAllWhitespace(kakaoAddress), StringUtils.trimAllWhitespace(naverAddress));
				}
				// 도로명주소 비교
				if(!ObjectUtils.isEmpty(kakaoRoadAddress) && !ObjectUtils.isEmpty(naverRoadAddress)) {
					roadAddressDistance = AlgorithmUtils.levinshteinDistance(StringUtils.trimAllWhitespace(kakaoRoadAddress), StringUtils.trimAllWhitespace(naverRoadAddress));
				}

				// 문자열 차이가 아래와 같을때 같다고 가정한다.
				// 1. 이름: 30%이하
				// 2. 주소: 30%이하
				double namePercentage = ((double)nameDistance / (double)maxNameLen); 
				double addressPercentage = ((double)addressDistance / (double)maxAddressLen);
				double roadAddressPercentage = ((double)roadAddressDistance / (double)maxRoadAddressLen);
				if(namePercentage <= 0.3 
						&& (addressPercentage <= 0.3 || roadAddressPercentage <= 0.3)) {
					// 공통일때 정보는 카카오 기준으로 작성
					PlaceInfo place = new PlaceInfo();
					place.setAddress(kakaoAddress);
					place.setCategory(kakaoItem.getCategoryName());
					place.setName(kakaoItem.getPlaceName());
					place.setPhone(kakaoItem.getPhone());
					place.setRoadAddress(kakaoRoadAddress);
					place.setUrl(kakaoItem.getPlaceUrl());
					places.add(place);
					
					log.info("공통 음식점: {}", place.toString());
					
					// 이미 검색되었는지는 해시테이블에 저장하여 사용
					kakaoSet.add(kakaoItem.getPlaceName());
					naverSet.add(naverItem.getTitle());
				}
			}
		}

		// 공통이 아닌 카카오 조회 결과
		for(DocumentInfo kakaoItem : kakaoResult.getDocuments()) {
			if(kakaoSet.contains(kakaoItem.getPlaceName())) continue;
			PlaceInfo place = new PlaceInfo();
			place.setAddress(kakaoItem.getAddressName());
			place.setCategory(kakaoItem.getCategoryName());
			place.setName(kakaoItem.getPlaceName());
			place.setPhone(kakaoItem.getPhone());
			place.setRoadAddress(kakaoItem.getRoadAddressName());
			place.setUrl(kakaoItem.getPlaceUrl());
			places.add(place);
		}
		
		// 공통이 아닌 네이버 조회 결과
		for(ItemInfo naverItem : naverResult.getItems()) {
			if(naverSet.contains(naverItem.getTitle())) continue;
			PlaceInfo place = new PlaceInfo();
			place.setAddress(naverItem.getAddress());
			place.setCategory(naverItem.getCategory());
			place.setName(naverItem.getTitle());
			place.setPhone(naverItem.getTelephone());
			place.setRoadAddress(naverItem.getRoadAddress());
			place.setUrl(naverItem.getLink());
			places.add(place);
		}
		
		SearchHistoryInfo searchHistory = SearchHistoryInfo.builder()
				.keyword(query)
				.build();
		
		searchHistoryRepository.save(searchHistory);

		PlaceApiResponse response = new PlaceApiResponse();
		response.setPlaces(places);

		return response;
	}


	@Transactional
	@Override
	public TrendApiResponse getTrendsQuery(MultiValueMap<String, String> header) {
		List<TrendInfo> trends = new ArrayList<>();
		List<SearchCount> searchCounts = searchHistoryRepository.getAllSearchHistory();
		for(SearchCount item : searchCounts) {
			TrendInfo trend = new TrendInfo(item.getKeyword(), item.getCount());
			trends.add(trend);
		}
		
		TrendApiResponse response = new TrendApiResponse();
		response.setTrends(trends);
		
		return response;
	}


	@Transactional
	@Override
	public TrendApiResponse getTrends(MultiValueMap<String, String> header) {
		List<TrendInfo> trends = new ArrayList<>();
		List<SearchHistoryInfo> searchHistoryInfos = searchHistoryRepository.findAll();
		Map<String, Long> countMap = new HashMap<>();
		
		searchHistoryInfos.forEach(item -> {
			String keyword = item.getKeyword();
			Long count = countMap.containsKey(keyword) ? countMap.get(keyword)+1 : 1;
			countMap.put(keyword, count);
		});
		
		// 검색 횟수 확인
		for(String key : countMap.keySet()) {
			TrendInfo trend = new TrendInfo(key, countMap.get(key));
			trends.add(trend);
		}
		
		// 역순 정렬
		trends = trends.stream().sorted(Comparator.comparingLong(TrendInfo::getCount).reversed()).collect(Collectors.toList());
		
		TrendApiResponse response = new TrendApiResponse();
		response.setTrends(trends);
		
		return response;
	}

}
