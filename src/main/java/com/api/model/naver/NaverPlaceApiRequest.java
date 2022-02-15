package com.api.model.naver;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaverPlaceApiRequest {
	@NotNull(message = "{validation.message.notnull}")
	String query;
	Integer display;
	Integer start;
	String sort;
}
