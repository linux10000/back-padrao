package br.com.juliano.appclient.structure.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

public class FormatUtil {

	public static final String PATTERN_PONTOS_TRACOS = "[./-]";
	public static final String PATTERN_TRACOS_PARENTESES = "[\\(\\)\\-\\s]";
	public static final String PATTERN_ELEMENTO_VAZIO =  "";
	public static final String PATTERN_PRIMEIRA_E_ULTIMA_PALAVRA = "(^\\s?\\w+\\b|(\\b\\w+)[\\.?!\\s]*$)";
	public static final String PATTERN_UUID = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
	public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
    public static String removeTracoPontos(String numeroDocumento) {
        return numeroDocumento.replaceAll(PATTERN_PONTOS_TRACOS, PATTERN_ELEMENTO_VAZIO).trim().toUpperCase();
    }

    public static String removeTracoParentes(String numeroTelefone) {
        return numeroTelefone.replaceAll(PATTERN_TRACOS_PARENTESES, PATTERN_ELEMENTO_VAZIO).trim().toUpperCase();
    }
   
    public static String obtemPrimeiraEUltimaPalavra(String value) {
    	List<String> r = new ArrayList<String>();
    	Matcher matcher = Pattern.compile(PATTERN_PRIMEIRA_E_ULTIMA_PALAVRA).matcher(value);
    	while (matcher.find() ) {
    		r.add(matcher.group(1));
    	};
    	return r.stream().collect(Collectors.joining(" "));
    }
    
	public static boolean emailValido(String valor){
		if ( Strings.isNullOrEmpty(valor) )
			return false;
		
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		return pattern.matcher(valor).matches();
	}
}
