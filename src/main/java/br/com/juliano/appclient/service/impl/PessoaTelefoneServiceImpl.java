package br.com.juliano.appclient.service.impl;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.juliano.appclient.model.PessoaTelefone;
import br.com.juliano.appclient.repository.PessoaTelefoneRepository;
import br.com.juliano.appclient.repository.to.PessoaTelefoneInputFilterTO;
import br.com.juliano.appclient.repository.to.PessoaTelefoneWithPessoaOutputFilter;
import br.com.juliano.appclient.service.PessoaTelefoneService;
import br.com.juliano.appclient.structure.exception.notfound.PessoaTelefoneNotFoundException;
import br.com.juliano.appclient.structure.props.MessagesProp;
import br.com.juliano.appclient.structure.validation.model.PessoaTelefoneValidation;
import br.com.juliano.appclient.structure.validation.model.PessoaTelefoneValidation.RULE;

@Service
@Validated
public class PessoaTelefoneServiceImpl implements PessoaTelefoneService {

	@Autowired
	private PessoaTelefoneRepository pessoaTelefoneDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PessoaTelefone insertDefault(@PessoaTelefoneValidation(rule = RULE.INSERT_DEFAULT) PessoaTelefone ptl) {
		return pessoaTelefoneDao.save(ptl);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PessoaTelefone updateDefault(@PessoaTelefoneValidation(rule = RULE.UPDATE_DEFAULT) PessoaTelefone ptl) {
		PessoaTelefone old = pessoaTelefoneDao.findById(ptl.getPtlnid()).orElseThrow(PessoaTelefoneNotFoundException::new);
		
		//nao deixo trocar a pessoa quem o telefone estah vinculado
		
		old.setPtlcnumero(ptl.getPtlcnumero());
		
		return pessoaTelefoneDao.save(old);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void deleteById(@NotNull(message = MessagesProp.PESSOA_TELEFONE_ID_NOT_NULL) Integer ptlnid) {
		pessoaTelefoneDao.deleteById(ptlnid);
	}
	
	@Override
	public List<PessoaTelefoneWithPessoaOutputFilter> filterWithNativeWithPessoa(PessoaTelefoneInputFilterTO filter) {
		return pessoaTelefoneDao.filterNativeWithPessoa(
				filter.getPtlnid(),
				filter.getPtlcnumero(),
				filter.getPesnid(), 
				filter.getPescnome(), 
				filter.getPescemail(), 
				filter.getQuickSearch(), 
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(), 
						Direction.fromOptionalString(filter.getSortDirection()).orElse(Direction.ASC), 
						Objects.requireNonNullElse(filter.getSortField(), "ptlnid")
						)
				);
	}
	
	@Override
	public List<PessoaTelefone> filterWithPessoa(PessoaTelefoneInputFilterTO filter) {
		return pessoaTelefoneDao.filterWithPessoa(
				filter.getPtlnid(),
				filter.getPtlcnumero(),
				filter.getPesnid(), 
				filter.getPescnome(), 
				filter.getPescemail(), 
				filter.getQuickSearch(), 
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(), 
						Direction.fromOptionalString(filter.getSortDirection()).orElse(Direction.ASC), 
						Objects.requireNonNullElse(filter.getSortField(), "ptlnid")
						)
				);
	}
}
