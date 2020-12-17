package br.com.juliano.appclient.structure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.juliano.appclient.structure.utils.Comuns;

@Configuration
public class JacksonConfig {

	@Bean()
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		return Comuns.getObjectMapper();
	}
}
