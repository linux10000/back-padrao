package br.com.juliano.appclient.structure.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope( BeanDefinition.SCOPE_SINGLETON )
public class SpringValidationUtil {

	@Autowired
	private MessageSource messageSource;

	
	public boolean isNotNull(Object value, String message, ConstraintValidatorContext context) {
		if ( !Objects.isNull(value) )
			return true;
			
		addMessage(message, context);
		return false;
	}
	
	public boolean isNotEmpty(String value, String message, ConstraintValidatorContext context) {
		if ( StringUtils.isNotBlank(value) )
			return true;
			
		addMessage(message, context);
		return false;
	}
	
	public boolean isNotEmpty(List<?> value, String message, ConstraintValidatorContext context) {
		if ( !Objects.isNull(value) && !value.isEmpty() )
			return true;
			
		addMessage(message, context);
		return false;
	}
	
	public boolean isNotGreaterThan(BigDecimal value, BigDecimal reference, String message, ConstraintValidatorContext context) {
		if ( !Objects.isNull(value) && value.compareTo(reference) == 1 )
			return true;
			
		addMessage(message, context, reference);
		return false;
	}
	
	public boolean isNotTrue(Boolean value, String message, ConstraintValidatorContext context) {
		if ( value )
			return true;
			
		addMessage(message, context);
		return false;
	} 

    public void addMessage(String mensagem, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(messageSource.getMessage(mensagem, new Object[] {}, mensagem, Locale.getDefault())).addConstraintViolation();
    }
    
    public void addMessage(String mensagem, ConstraintValidatorContext context, Object ...params) {
        ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate(messageSource.getMessage(mensagem, params, mensagem, Locale.getDefault()));
        builder.addConstraintViolation();
    }
}
