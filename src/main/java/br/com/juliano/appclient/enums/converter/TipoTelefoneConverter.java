package br.com.juliano.appclient.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.juliano.appclient.enums.TipoTelefone;

@Converter(autoApply = true)
public class TipoTelefoneConverter implements AttributeConverter<TipoTelefone, Integer> {

	@Override
	public Integer convertToDatabaseColumn(TipoTelefone arg0) {
		return arg0.getCode();
	}

	@Override
	public TipoTelefone convertToEntityAttribute(Integer arg0) {
		return TipoTelefone.getFromCode(arg0).orElse(TipoTelefone.NOT_AVAILABLE);
	}

}
