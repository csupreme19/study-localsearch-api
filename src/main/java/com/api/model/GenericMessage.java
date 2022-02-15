package com.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * API 서버 표준 응답 메시지
 * 
 * @author csupreme19
 * @since 2022.02.12
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericMessage {
	public GenericMessage() {
		this.code = "200";
		this.message = "Ok";
	}
	
	String code;
	transient String message;
	Object result;
}
