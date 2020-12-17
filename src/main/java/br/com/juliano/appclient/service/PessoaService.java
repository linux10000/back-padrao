package br.com.juliano.appclient.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import br.com.juliano.appclient.model.Pessoa;
import br.com.juliano.appclient.repository.to.PessoaInputFilterTO;
import br.com.juliano.appclient.repository.to.PessoaWithCountOutputFilter;
import br.com.juliano.appclient.structure.props.MessagesProp;
import br.com.juliano.appclient.structure.validation.model.PessoaValidation;
import br.com.juliano.appclient.structure.validation.model.PessoaValidation.RULE;

public interface PessoaService {

	Pessoa insertDefault(@PessoaValidation(rule = RULE.INSERT_DEFAULT) Pessoa pes);

	Pessoa updateDefault(@PessoaValidation(rule = RULE.UPDATE_DEFAULT) Pessoa pes);

	void deleteById(@NotNull(message = MessagesProp.PESSOA_ID_NOT_NULL)Integer pesnid);

	Page<Pessoa> filterWithSpringCount(PessoaInputFilterTO filter);

	List<Pessoa> filterWithoutCount(PessoaInputFilterTO filter);

	List<PessoaWithCountOutputFilter> filterWithNativeWindowCount(PessoaInputFilterTO filter);
}
