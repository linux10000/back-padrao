package br.com.juliano.appclient.structure.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemProperties {
	private static final String _JULIANO_CACHE_TTL_IN_SECONDS = "juliano.cache.ttl.in.seconds";

	@Value("${" + _JULIANO_CACHE_TTL_IN_SECONDS + "}")
	private Integer cacheTtlInSeconds;
	
	@PostConstruct
	public void init() {
		//arquivo ehcache.xml nao pega as props direto do application.yml
		System.setProperty(_JULIANO_CACHE_TTL_IN_SECONDS, cacheTtlInSeconds.toString());
	}
}
