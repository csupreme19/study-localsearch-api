package com.api.model.naver;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaverPlaceApiRequest {
	@NotNull(message = "{validation.not-null}")
	String query;
	Integer display;
	Integer start;
	String sort;
}
