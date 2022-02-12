package com.api.controller.rest;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 장소 검색 REST API 서버
 * 
 * @author csupreme19
 * @since 2022.02.12
 *
 */
@RepositoryRestController
public class ApiRestController {

	/**
	 * 장소 검색 API
	 * 
	 * 카카오 검색 API, 네이버 검색 API 호출하여 각각 5개의 장소를 가져와 10개로 보여준다.
	 */
	@GetMapping(path = "/place")
	public @ResponseBody CollectionModel<Item> getPlaces(@PageableDefault Pageable pageable){
		return null;
	}
	
}
