package com.api.exception.handler;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
		GenericMessage response = new GenericMessage();
		response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		StringBuilder sb = new StringBuilder();
		for(ObjectError error : ex.getAllErrors()) {
			if(sb.length() != 0) sb.append("\n");
			String fieldName = ((FieldError) error).getField();
			String message = String.format(ex.getFieldError().getDefaultMessage(), fieldName);
			sb.append(message);
		}
		response.setMessage(sb.toString());
		return response;
	}
	
	/**
	 * 400 예외 처리
	 * HTTP STATUS: 400 BAD REQUEST
	 */
	@ExceptionHandler(WebExchangeBindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GenericMessage error400(WebExchangeBindException ex) {
		log.error(ex.getLocalizedMessage());
		GenericMessage response = new GenericMessage();
		response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		StringBuilder sb = new StringBuilder();
		for(ObjectError error : ex.getAllErrors()) {
			if(sb.length() != 0) sb.append("\n");
			String fieldName = ((FieldError) error).getField();
			String message = String.format(ex.getFieldError().getDefaultMessage(), fieldName);
			sb.append(message);
		}
		response.setMessage(sb.toString());
		return response;
	}
	
	/**
	 * 404 예외 처리 
	 * HTTP STATUS: 404 NOT FOUND
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public GenericMessage error404(NoHandlerFoundException ex) {
		log.error(ex.getLocalizedMessage());
		GenericMessage response = new GenericMessage();
		response.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
		String msg = messageSource.getMessage("error.not-found", new String[] {ex.getRequestURL().toString()}, Locale.getDefault());
		response.setMessage(msg);
		return response;
	}
	
	/**
	 * 405 예외 처리
	 * HTTP STATUS: 405 METHOD NOT ALLOWED
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public GenericMessage error405(HttpRequestMethodNotSupportedException ex) {
		log.error(ex.getLocalizedMessage());
		GenericMessage response = new GenericMessage();
		response.setCode(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()));
		String message = messageSource.getMessage("error.method-not-allowed", new String[] {ex.getMethod()}, Locale.getDefault());
		response.setMessage(message);
		return response;
	}
	
	/**
	 * 기본 예외 처리
	 * HTTP STATUS: 500 INTERNAL SERVER ERROR
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public GenericMessage error500(Exception ex) {
		log.error(ex.getLocalizedMessage());
		GenericMessage response = new GenericMessage();
		response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		String message = messageSource.getMessage("error.internal-server-error", null, Locale.getDefault());
		response.setMessage(message);
		return response;
	}
}
