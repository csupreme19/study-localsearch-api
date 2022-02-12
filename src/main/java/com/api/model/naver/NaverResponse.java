package com.api.model.naver;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaverResponse {
	String lastBuildDate;
	Integer total;
	Integer start;
	Integer display;
	List<ItemInfo> items;
}
