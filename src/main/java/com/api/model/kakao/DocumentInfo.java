package com.api.model.kakao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DocumentInfo {
	String id;
	String placeName;
	String categoryName;
	String categoryGroupCode;
	String categoryGroupName;
	String phone;
	String addressName;
	String roadAddressName;
	String x;
	String y;
	String placeUrl;
	String distance;
}
