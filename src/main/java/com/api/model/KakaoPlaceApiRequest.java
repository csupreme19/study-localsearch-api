package com.api.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class KakaoPlaceApiRequest {
	@NotNull
	String query;
	String categoryGroupCode;
	String x;
	String y;
	String radius;
	String rect;
	String page;
	String size;
	String sort;
}
