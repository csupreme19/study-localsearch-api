package com.api.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceApiRequest {
	@NotNull(message = "{validation.message.notnull}")
	String query;
}
