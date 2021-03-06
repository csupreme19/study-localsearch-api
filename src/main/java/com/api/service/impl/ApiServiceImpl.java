package com.api.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
import com.api.constants.ApiParameters;
import com.api.constants.RegexPatterns;
import com.api.model.PlaceApiRequest;
import com.api.model.PlaceApiResponse;
import com.api.model.PlaceInfo;
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
		String query = request.getQuery();
		KakaoPlaceApiRequest kakaoRequest = KakaoPlaceApiRequest.builder()
				.query(query)
				.size(ApiParameters.KAKAO_SEARCH_SIZE)
				.build();
		Mono<KakaoPlaceApiResponse> kakaoResponse = WebClientUtil.get(ApiHosts.API_SERVER.getUrl()+ApiEndpoints.KAKAO_SEARCH, header, ObjectMapperUtil.parseMap(kakaoRequest))
				.bodyToMono(KakaoPlaceApiResponse.class);
		

		NaverPlaceApiRequest naverRequest = NaverPlaceApiRequest.builder()
				.query(query)
				.display(ApiParameters.NAVER_SEARCH_DISPLAY)
				.build();
		Mono<NaverPlaceApiResponse> naverResponse = WebClientUtil.get(ApiHosts.API_SERVER.getUrl()+ApiEndpoints.NAVER_SEARCH, header, ObjectMapperUtil.parseMap(naverRequest))
				.bodyToMono(NaverPlaceApiResponse.class);;

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
				
				// HTML ?????? ??????
				if(!ObjectUtils.isEmpty(naverName)) naverName = naverName.replaceAll(htmlTagRegex, "");

				if(!Pattern.matches(addressRegex, naverAddress) && !Pattern.matches(addressRegex, naverRoadAddress)) continue;
				if(!Pattern.matches(addressRegex, naverAddress)) naverAddress = "";
				if(!Pattern.matches(addressRegex, naverRoadAddress)) naverRoadAddress = "";

				// ?????? ???????????? ??????
				if(isSameString(kakaoName, naverName)
						&& (isSameString(kakaoAddress, naverAddress) || isSameString(kakaoRoadAddress, naverRoadAddress))) {
					// ???????????? ????????? ????????? ???????????? ??????
					PlaceInfo place = PlaceInfo.builder()
							.address(kakaoAddress)
							.category(kakaoItem.getCategoryName())
							.name(kakaoName)
							.phone(kakaoItem.getPhone())
							.roadAddress(kakaoRoadAddress)
							.url(kakaoItem.getPlaceUrl())
							.build();
					places.add(place);
					
					log.info("?????? ??????: {}", place.toString());
					
					// ?????? ?????????????????? ?????????????????? ??????
					kakaoSet.add(kakaoItem.getPlaceName());
					naverSet.add(naverItem.getTitle());
				}
			}
		}

		// ????????? ?????? ????????? ?????? ??????
		for(DocumentInfo kakaoItem : kakaoResult.getDocuments()) {
			if(kakaoSet.contains(kakaoItem.getPlaceName())) continue;
			PlaceInfo place = PlaceInfo.builder()
					.address(kakaoItem.getAddressName())
					.category(kakaoItem.getCategoryName())
					.name(kakaoItem.getPlaceName())
					.phone(kakaoItem.getPhone())
					.roadAddress(kakaoItem.getRoadAddressName())
					.url(kakaoItem.getPlaceUrl())
					.build();
			places.add(place);
		}
		
		// ????????? ?????? ????????? ?????? ??????
		for(ItemInfo naverItem : naverResult.getItems()) {
			if(naverSet.contains(naverItem.getTitle())) continue;
			PlaceInfo place = PlaceInfo.builder()
					.address(naverItem.getAddress())
					.category(naverItem.getCategory())
					.name(naverItem.getTitle())
					.phone(naverItem.getTelephone())
					.roadAddress(naverItem.getRoadAddress())
					.url(naverItem.getLink())
					.build();
			places.add(place);
		}
		
		// ?????? ??? ????????????
		updateKeyword(query);

		PlaceApiResponse response = new PlaceApiResponse();
		response.setPlaces(places);

		return response;
	}
	
	/**
	 * ????????? ????????? ???????????? ????????? ????????????.
	 * ??????: ????????? ????????? 30% ??????
	 */
	private boolean isSameString(String str1, String str2) {
		int length =  Math.max(str1.length(), str2.length());
		int distance = length;

		if(!ObjectUtils.isEmpty(str1) && !ObjectUtils.isEmpty(str2)) {
			distance = AlgorithmUtils.levinshteinDistance(StringUtils.trimAllWhitespace(str1), StringUtils.trimAllWhitespace(str2));
		}
		double namePercentage = ((double)distance / (double)length);
		return namePercentage <= 0.3;
	}
	
	@Transactional
	private void updateKeyword(String query) {
		SearchHistoryInfo item = searchHistoryRepository.findOneByKeyword(query);
		if(ObjectUtils.isEmpty(item)) {
			item = searchHistoryRepository.save(SearchHistoryInfo.builder()
			.keyword(query)
			.count(0L)
			.build());
		}
		SearchHistoryInfo update = SearchHistoryInfo.builder()
				.keyword(item.getKeyword())
				.count(item.getCount()+1)
				.build();
		update = searchHistoryRepository.save(update);
	}


	@Transactional
	@Override
	public TrendApiResponse getTrends(MultiValueMap<String, String> header) {
		TrendApiResponse response = new TrendApiResponse();
		List<TrendInfo> trends = new ArrayList<>();
		List<SearchHistoryInfo> searchHistoryInfos = searchHistoryRepository.findAll();
		if(ObjectUtils.isEmpty(searchHistoryInfos)) return response;
		
		// ?????? ?????? ??????
		for(SearchHistoryInfo item : searchHistoryInfos) {
			TrendInfo trend = new TrendInfo(item.getKeyword(), item.getCount());
			trends.add(trend);
		}
		
		// ?????? ??????
		trends = trends.stream()
				.sorted(Comparator.comparingLong(TrendInfo::getCount).reversed())
				.limit(10)
				.collect(Collectors.toList());
		
		response.setTrends(trends);
		
		return response;
	}

}
