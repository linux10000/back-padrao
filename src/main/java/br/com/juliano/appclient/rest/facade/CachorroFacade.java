package br.com.juliano.appclient.rest.facade;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.juliano.appclient.service.to.CachorroTO;
import br.com.juliano.appclient.structure.config.CacheConfig;

@Component
public class CachorroFacade {
	private static final String _DOG_CACHE = "dog_cache";
	private static final String _URL_OBTEM_DOG = "https://dog.ceo/api/breeds/image/random";

	//cacheManager caso tenha mais de um cache manager no projeto
	@Cacheable(cacheManager = CacheConfig.EH_CACHE_MANAGER, cacheNames = _DOG_CACHE, key = "#pesnid", unless = "#result == null")
	public CachorroTO getByPessoaId(Integer pesnid) {
		WebClient webClient = WebClient.builder()
			    .baseUrl(_URL_OBTEM_DOG)
			    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			    .build();
		
		return webClient.get().retrieve().bodyToMono(CachorroTO.class).block();
	}

	@CacheEvict(cacheManager = CacheConfig.EH_CACHE_MANAGER, cacheNames = _DOG_CACHE, allEntries = true)
	public void clearAllDogs() {}
}
