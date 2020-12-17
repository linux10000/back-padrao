package br.com.juliano.appclient.structure.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.google.common.base.Strings;

public class Conversao {
	
	public static final String OFFSETDATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ";
	public static final String LOCALDATE_FORMAT = "yyyy-MM-dd";
	public static final String LOCALDATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static OffsetDateTime stringToOffsetDateTime(String obj, boolean throwException){
		if ( Strings.isNullOrEmpty(obj) )
			return null;
		
		OffsetDateTime r;
		try {
			r = OffsetDateTime.parse(obj, DateTimeFormatter.ofPattern(OFFSETDATETIME_FORMAT));
		} catch (Exception e) {
			if ( throwException )
				throw e;
			else r = null;
		}
		
		return r;
	}
	
	
	public static String OffsetDateTimeToString(OffsetDateTime obj){
		if ( obj == null )
			return null;
		return obj.format(DateTimeFormatter.ofPattern(OFFSETDATETIME_FORMAT));
	}
	
	public static String localDateToString(LocalDate obj){
		if ( obj == null )
			obj = LocalDate.of(1980, 1, 1);
		return obj.format(DateTimeFormatter.ofPattern(LOCALDATE_FORMAT));
	}
	
	public static LocalDateTime stringTolocalDateTime(String obj, boolean throwException){
		if ( Strings.isNullOrEmpty(obj) )
			return LocalDateTime.of(1980, 1, 1, 0, 0, 0);
		
		LocalDateTime r;
		try {
			r = LocalDateTime.parse(obj, DateTimeFormatter.ofPattern(LOCALDATETIME_FORMAT));
		} catch (Exception e) {
			if ( throwException )
				throw e;
			else r = null;
		}
		
		return r;
	}
	
	public static String localDateTimeToString(LocalDateTime obj){
		if ( obj == null )
			obj = LocalDateTime.of(1980, 1, 1, 0, 0, 0);
		return obj.format(DateTimeFormatter.ofPattern(LOCALDATETIME_FORMAT));
	}
	
	public static LocalDate stringToLocalDate(String obj, boolean throwException){
		if ( Strings.isNullOrEmpty(obj) )
			return LocalDate.of(1980, 1, 1);
		
		LocalDate r;
		try {
			r = LocalDate.parse(obj, DateTimeFormatter.ofPattern(LOCALDATE_FORMAT));
		} catch (Exception e) {
			if ( throwException )
				throw e;
			else r = null;
		}
		
		return r;
	}
}
