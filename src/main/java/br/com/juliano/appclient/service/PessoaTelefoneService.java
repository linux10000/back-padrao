package br.com.juliano.appclient.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.juliano.appclient.model.PessoaTelefone;
import br.com.juliano.appclient.repository.to.PessoaTelefoneInputFilterTO;
import br.com.juliano.appclient.repository.to.PessoaTelefoneWithPessoaOutputFilter;
import br.com.juliano.appclient.structure.props.MessagesProp;
import br.com.juliano.appclient.structure.validation.model.PessoaTelefoneValidation;
import br.com.juliano.appclient.structure.validation.model.PessoaTelefoneValidation.RULE;

public interface PessoaTelefoneService {

	PessoaTelefone insertDefault(@PessoaTelefoneValidation(rule = RULE.INSERT_DEFAULT) PessoaTelefone ptl);

	PessoaTelefone updateDefault(@PessoaTelefoneValidation(rule = RULE.UPDATE_DEFAULT) PessoaTelefone ptl);

	void deleteById(@NotNull(message = MessagesProp.PESSOA_TELEFONE_ID_NOT_NULL) Integer ptlnid);

	List<PessoaTelefoneWithPessoaOutputFilter> filterWithNativeWithPessoa(PessoaTelefoneInputFilterTO filter);

	List<PessoaTelefone> filterWithPessoa(PessoaTelefoneInputFilterTO filter);

}
