package com.api.aop;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Aspect
public class LogAspect {

	@Around("execution(* com.api.controller.rest.*.*(..))")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpHeaders headers = getHeaders(request);
		String method = request.getMethod();
		String query = request.getQueryString();
		String host = request.getServerName() + ":" + request.getServerPort();
		String uri = request.getRequestURI();
		String url = host + uri + "?" + query;
		
		long start = System.currentTimeMillis();
		log.info("\n==================================================" 
				+ "\nRequest: {}) {}"
				+ "\nHeaders: {}"
				+ "\n=================================================="
				, method, url
				, headers);

		Object result = (ResponseEntity<?>) pjp.proceed(pjp.getArgs());
		
		if(result instanceof Mono) {
			return result;
		}
		ResponseEntity<?> response = null;
		if(result instanceof ResponseEntity) {
			response = (ResponseEntity<?>)result;
		}
		Object object = ObjectUtils.isEmpty(response) ? null : response.getBody();
		HttpStatus status = ObjectUtils.isEmpty(response) ? null : response.getStatusCode();
		String body = ObjectUtils.isEmpty(object) ? "" : object.toString();
		String duration = String.valueOf(System.currentTimeMillis() - start);
		headers = ObjectUtils.isEmpty(response) ? null : response.getHeaders();
		log.info("\n==================================================" 
				+ "\nResponse: {}) {}"
				+ "\nStatus  : {} {}"
				+ "\nTime    : {}ms"
				+ "\nHeaders : {}"
				+ "\nBody    : {}"
				+ "\n=================================================="
				, method, url
				, status.value(), status.name()
				, duration
				, headers
				, body);

		return result;
	}

	private HttpHeaders getHeaders(HttpServletRequest request) {
		return Collections
				.list(request.getHeaderNames())
				.stream()
				.collect(Collectors.toMap(
						Function.identity(), 
						header -> Collections.list(request.getHeaders(header)), 
						(oldVal, newVal) -> newVal,
						HttpHeaders::new
						));
	}
}
