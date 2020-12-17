package br.com.juliano.appclient.repository.to;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Validated
@EqualsAndHashCode(callSuper = false)
public class PessoaTelefoneInputFilterTO extends BaseInputFilterTO {

	@JsonProperty("id")
	private Integer ptlnid;
	
	@JsonProperty("numero")
	private String ptlcnumero;
	
	@JsonProperty("pessoaId")
	private Integer pesnid;
	
	@JsonProperty("pessoaNome")
	private String pescnome;
	
	@JsonProperty("pessoaEmail")
	private String pescemail;
}
