package br.com.juliano.appclient.structure.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.jcache.config.JCacheConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfig extends JCacheConfigurerSupport {
	public static final String EH_CACHE_MANAGER = "ehCacheManager";
	private static final String EH_CACHE_FILE = "ehcache.xml";

	@Bean(name = EH_CACHE_MANAGER) //caso tenha mais de um cache manager
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource(EH_CACHE_FILE));
		cmfb.setShared(true);
		return cmfb;
	}
}
