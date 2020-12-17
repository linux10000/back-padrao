package br.com.juliano.appclient.enums;

import java.util.Optional;
import java.util.stream.Stream;

import br.com.juliano.appclient.consts.DominioConst;
import br.com.juliano.appclient.consts.GeralConst;
import lombok.Getter;

@Getter
public enum TipoTelefone {
	NOT_AVAILABLE(GeralConst.NOT_AVAILABLE_INT), //prefira nao trabalhar com nulos, tenha um valor padrao tanto quanto possivel
	CASA(DominioConst.PESSOA_TELEFONE_TIPO.CASA),
	CELULAR(DominioConst.PESSOA_TELEFONE_TIPO.CELULAR),
	TRABALHO(DominioConst.PESSOA_TELEFONE_TIPO.TRABALHO);

	//toda vez q o metodo 'values()' eh chamado eh feito um clone
	//entao deixei nessa constante para fazer uma vez soh durante toda a execucao da aplicacao
	private static final TipoTelefone[] _VALORES = values(); 
	
	private Integer code;
	
	TipoTelefone(Integer code) {
		this.code = code;
	}
	
	public static Optional<TipoTelefone> getFromCode(Integer code) {
		return Stream.of(_VALORES)
				.filter(v -> v.getCode().equals(code))
				.findFirst();
	}
	
	public static Optional<TipoTelefone> getFromName(String name) {
		return Stream.of(_VALORES)
				.filter(v -> v.name().equals(name))
				.findFirst();
	}
}
