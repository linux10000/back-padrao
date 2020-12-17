package br.com.juliano.appclient.repository.to;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Validated
@EqualsAndHashCode(callSuper = false)
public class PessoaInputFilterTO extends BaseInputFilterTO {

	@JsonProperty("id")
	private Integer pesnid;
	
	@JsonProperty("nome")
	private String pescnome;
	
	@JsonProperty("email")
	private String pescemail;
}
