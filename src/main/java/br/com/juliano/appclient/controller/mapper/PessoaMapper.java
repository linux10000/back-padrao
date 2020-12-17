package br.com.juliano.appclient.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.juliano.appclient.controller.request.PessoaEditRequest;
import br.com.juliano.appclient.controller.request.PessoaInsertRequest;
import br.com.juliano.appclient.controller.response.PessoaDefaultResponse;
import br.com.juliano.appclient.controller.response.PessoaFilterPaginationResponse;
import br.com.juliano.appclient.model.Pessoa;

@Component
public class PessoaMapper {
	
	public Pessoa to(PessoaInsertRequest pojo) {
		return Pessoa.builder()
				.pescnome(pojo.getNome())
				.pescemail(pojo.getEmail())
				.build();
	}
	
	public Pessoa to(PessoaEditRequest pojo) {
		return Pessoa.builder()
				.pesnid(pojo.getId())
				.pescnome(pojo.getNome())
				.pescemail(pojo.getEmail())
				.build();
	}

	public PessoaDefaultResponse toDefaultResponse(Pessoa pes) {
		return PessoaDefaultResponse.builder()
				.id(pes.getPesnid())
				.nome(pes.getPescnome())
				.email(pes.getPescemail())
				.build();
	}
	
	public PessoaFilterPaginationResponse toFilterResponse(Page<Pessoa> lista) {
		return PessoaFilterPaginationResponse.builder()
				.totalCount(lista.getTotalElements())
				.totalPages(lista.getTotalPages())
				.pageIndex(lista.getNumber())
				.data(lista.getContent().stream().map(this::toDefaultResponse).collect(Collectors.toList()))
				.build();
	}
}
