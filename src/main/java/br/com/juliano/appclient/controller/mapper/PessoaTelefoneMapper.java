package br.com.juliano.appclient.controller.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.juliano.appclient.controller.request.PessoaTelefoneEditRequest;
import br.com.juliano.appclient.controller.request.PessoaTelefoneInsertRequest;
import br.com.juliano.appclient.controller.response.PessoaTelefoneDefaultResponse;
import br.com.juliano.appclient.enums.TipoTelefone;
import br.com.juliano.appclient.model.Pessoa;
import br.com.juliano.appclient.model.PessoaTelefone;

@Component
public class PessoaTelefoneMapper {
	
	@Lazy
	@Autowired
	private PessoaMapper pessoaMapper;
	
	public PessoaTelefone to(PessoaTelefoneInsertRequest pojo) {
		return PessoaTelefone.builder()
				.ptlnpesFK(new Pessoa(pojo.getPessoaId())) //isso faz com que lah na camada de service seja feito automaticamente um select (conferir no console) para verificar se a pessoa realmente e um 'auto attach' para virar um valor 'gravavel' dentro do contexto
				.ptlcnumero(pojo.getNumero())
				.ptlntipoEnum(TipoTelefone.getFromName(pojo.getTipo()).orElse(TipoTelefone.NOT_AVAILABLE))
				.build();
	}
	
	public PessoaTelefone to(PessoaTelefoneEditRequest pojo) {
		return PessoaTelefone.builder()
				.ptlnid(pojo.getId())
				.ptlcnumero(pojo.getNumero())
				.ptlntipoEnum(TipoTelefone.getFromName(pojo.getTipo()).orElse(TipoTelefone.NOT_AVAILABLE))
				.build();
	}	

	public PessoaTelefoneDefaultResponse toDefaultResponse(PessoaTelefone ptl) {
		return PessoaTelefoneDefaultResponse.builder()
				.id(ptl.getPtlnid())
				.pessoa(pessoaMapper.toDefaultResponse(ptl.getPtlnpesFK()))
				.numero(ptl.getPtlcnumero())
				.tipo(ptl.getPtlntipoEnum().name())
				.build();
	}
}
