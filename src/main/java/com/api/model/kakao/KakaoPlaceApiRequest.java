package com.api.model.kakao;



import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KakaoPlaceApiRequest {
	@NotNull(message = "{validation.message.notnull}")
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
