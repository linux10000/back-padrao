package br.com.juliano.appclient.repository.custom;

import java.util.List;

import org.springframework.data.domain.Pageable;

import br.com.juliano.appclient.model.PessoaTelefone;
import br.com.juliano.appclient.repository.to.PessoaTelefoneWithPessoaOutputFilter;

public interface CustomPessoaTelefoneRepository {

	List<PessoaTelefoneWithPessoaOutputFilter> filterNativeWithPessoa(Integer ptlnid, String ptlcnumero, Integer pesnid,
			String pescnome, String pescemail, String quickSearch, Pageable pag);

	List<PessoaTelefone> filterWithPessoa(Integer ptlnid, String ptlcnumero, Integer pesnid, String pescnome,
			String pescemail, String quickSearch, Pageable pag);

}
