package com.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceInfo {
	String name;
	String url;
	String category;
	String phone;
	String address;
	String roadAddress;
}
