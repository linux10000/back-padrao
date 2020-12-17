package br.com.juliano.appclient.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PessoaTelefoneDefaultResponse {

	private Integer id;
	private PessoaDefaultResponse pessoa;
	private String numero;
	private String tipo;
}
