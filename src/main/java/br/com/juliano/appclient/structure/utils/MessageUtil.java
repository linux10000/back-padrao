package br.com.juliano.appclient.structure.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

	@Autowired
	private MessageSource messageSource;
	
	public String resolveMessage(String msg) {
		return resolveMessage(msg, new Object[] {}, Locale.getDefault());
	}
	
	public String resolveMessage(String msg, Object[] args, Locale locale) {
		return messageSource.getMessage(msg, new Object[] {}, msg, LocaleContextHolder.getLocale());
	}
}
