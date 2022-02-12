package com.api.utils;

import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectMapperUtil {
	private static ObjectMapper mapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	public static MultiValueMap<String, String> parseMap(Object object) {
		try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            Map<String, String> map = mapper.convertValue(object, new TypeReference<Map<String, String>>() {});
            params.setAll(map);
            
            return params;
        } catch (Exception e) {
            log.error("Object param error {}, {}", object, e);
            return null;
        }
	}
}
