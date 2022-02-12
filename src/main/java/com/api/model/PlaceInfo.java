package com.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceInfo {
	String name;
	String url;
	String category;
	String phone;
	String address;
	String roadAddress;
}
