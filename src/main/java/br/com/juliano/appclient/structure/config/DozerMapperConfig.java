package br.com.juliano.appclient.structure.config;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import br.com.juliano.appclient.structure.outros.DozenFieldMapper;

@Configuration
public class DozerMapperConfig {

	@Bean()
	@Primary
	public DozerBeanMapper dozer() {
		DozerBeanMapper mapper;
		
		List<String> mappingFiles = new ArrayList<String>();
		mappingFiles.add("dozerBeanMapping.xml");
		mapper = new DozerBeanMapper();
		mapper.setMappingFiles(mappingFiles);
		mapper.setCustomFieldMapper(new DozenFieldMapper());
		
		return mapper;
	}
}
