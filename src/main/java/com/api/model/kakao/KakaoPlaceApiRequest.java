package com.api.model.kakao;



import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KakaoPlaceApiRequest {
	@NotNull(message = "{validation.not-null}")
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
