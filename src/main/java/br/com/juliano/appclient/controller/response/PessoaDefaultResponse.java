package br.com.juliano.appclient.controller.response;

import br.com.juliano.appclient.service.to.CachorroTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PessoaDefaultResponse {

	private Integer id;
	private String nome;
	private String email;
	private CachorroTO cachorro;
	
	public PessoaDefaultResponse setCachorro(CachorroTO to) {
		this.cachorro = to;
		return this;
	}
}
