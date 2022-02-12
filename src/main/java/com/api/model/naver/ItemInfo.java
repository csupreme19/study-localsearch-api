package com.api.model.naver;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemInfo {
	String title;
	String linke;
	String category;
	String description;
	String telephone;
	String address;
	String roadAddress;
	String mapx;
	String mapy;
}
