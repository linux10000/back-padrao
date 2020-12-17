package br.com.juliano.appclient.repository.to;

import br.com.juliano.appclient.enums.TipoTelefone;
import lombok.Data;

@Data
public class PessoaTelefoneWithPessoaOutputFilter {

	private Integer ptlnid;
	private String ptlcnumero;
	
	private Integer pesnid;
	private String pescnome;
	private String infoConfidencial;
	private String pescemail;
	private TipoTelefone tipo;
}
