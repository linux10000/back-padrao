package br.com.juliano.appclient.structure.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class Comuns {
	
	private static ObjectMapper objectMapper;
	
	public static ObjectMapper getObjectMapper(){
		if ( objectMapper == null ){
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			objectMapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
			objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
			objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			objectMapper.registerModule(new ParameterNamesModule());
			objectMapper.registerModule(new Jdk8Module());
			
			objectMapper.setSerializationInclusion(Include.NON_NULL);	
			
			JavaTimeModule javaTimeModule = new JavaTimeModule();
			javaTimeModule.addSerializer(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
				@Override
				public void serialize(OffsetDateTime value, JsonGenerator gen,
						SerializerProvider serializers) throws IOException {					
					gen.writeString(Conversao.OffsetDateTimeToString(value));
				}
			});
			
			javaTimeModule.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
				@Override
				public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException {
					return Conversao.stringToOffsetDateTime(p.getValueAsString(), true);
				}
			});
			
			javaTimeModule.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
				@Override
				public void serialize(LocalDate value, JsonGenerator gen,
						SerializerProvider serializers) throws IOException {					
					gen.writeString(Conversao.localDateToString(value));
				}
			});
			
			javaTimeModule.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
				@Override
				public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException {
					return Conversao.stringToLocalDate(p.getValueAsString(), true);
				}
			});
			
			objectMapper.registerModule(javaTimeModule);
			
		}
		
		return objectMapper;
	}
	
    public static boolean checkAll(final Boolean... values) {
        if (values == null) 
            return false;

        boolean result = true;
        
        //nao usei stream aqui pq for eh mais performatico e como esse codigo nao vai ser duplicado, achei isso aceitavel
        for (final Boolean val : values) {
            if ( !val ) {
                result = false;
            }
        }

        return result;
    }
}
