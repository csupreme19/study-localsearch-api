package com.api.utils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

/**
 * WebClient 사용을 위한 유틸
 * 
 * @author csupreme19
 * @since 2022.02.12
 */
@Component
public class WebClientUtil {

	private static WebClient client;

	/**
	 * WebClient 초기화
	 */
	public WebClientUtil() {
		WebClient client = WebClient.builder()
				.baseUrl("http://localhost:8080")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
				.clientConnector(new ReactorClientHttpConnector(HttpClient.create()
						.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
						.responseTimeout(Duration.ofMillis(5000))
						.doOnConnected(conn -> 
						conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
						.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)))))
				.build();
		this.client = client;
	}

	/**
	 * GET Non-blocking 요청
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static ResponseSpec get(String url, MultiValueMap<String, String> headers, MultiValueMap<String, String> params) {
		UriSpec<?> uriSpec = client.get();
		
		RequestHeadersSpec<?> requestHeadersSpec = client.get()
				.uri(url, uriBuilder -> 
				uriBuilder.queryParams(params)
				.build())
				.headers(httpHeaders -> {
					httpHeaders.addAll(headers);
				});

		ResponseSpec responseSpec = requestHeadersSpec.accept(MediaType.APPLICATION_JSON)
				.acceptCharset(StandardCharsets.UTF_8)
				.retrieve();
		
		return responseSpec;
	}

}
