package com.api.exception.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.api.model.GenericMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * RestController 예외 처리 어드바이스
 * 
 * @author csupreme19
 * @since 2022.02.13
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
	
	@Autowired
	MessageSource messageSource;
	
	/**
	 * 400 예외 처리
	 * HTTP STATUS: 400 BAD REQUEST
	 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GenericMessage error400(BindException ex) {
		log.error(ex.getLocalizedMessage());
		GenericMessage message = new GenericMessage();
		message.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		StringBuilder sb = new StringBuilder();
		for(ObjectError error : ex.getAllErrors()) {
			if(sb.length() != 0) sb.append("\n");
			String fieldName = ((FieldError) error).getField();
			String errMsg = String.format(ex.getFieldError().getDefaultMessage(), fieldName);
			sb.append(errMsg);
		}
		message.setMessage(sb.toString());
		return message;
	}
	
	/**
	 * 400 예외 처리
	 * HTTP STATUS: 400 BAD REQUEST
	 */
	@ExceptionHandler(WebExchangeBindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GenericMessage error400(WebExchangeBindException ex) {
		log.error(ex.getLocalizedMessage());
		GenericMessage message = new GenericMessage();
		message.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		StringBuilder sb = new StringBuilder();
		for(ObjectError error : ex.getAllErrors()) {
			if(sb.length() != 0) sb.append("\n");
			String fieldName = ((FieldError) error).getField();
			String errMsg = String.format(ex.getFieldError().getDefaultMessage(), fieldName);
			sb.append(errMsg);
		}
		message.setMessage(sb.toString());
		return message;
	}
	
	/**
	 * 404 예외 처리 
	 * HTTP STATUS: 404 NOT FOUND
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public GenericMessage error404(NoHandlerFoundException ex) {
		log.error(ex.getLocalizedMessage());
		GenericMessage message = new GenericMessage();
		message.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
		message.setMessage(new StringBuilder().append(ex.getRequestURL()).append(" 주소를 찾을 수 없습니다.").toString());
		return message;
	}
	
	/**
	 * 기본 예외 처리
	 * HTTP STATUS: 500 INTERNAL SERVER ERROR
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public GenericMessage error500(Exception ex) {
		log.error(ex.getLocalizedMessage());
		ex.printStackTrace();
		GenericMessage message = new GenericMessage();
		message.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		message.setMessage("내부 서버 오류");
		return message;
	}
}
