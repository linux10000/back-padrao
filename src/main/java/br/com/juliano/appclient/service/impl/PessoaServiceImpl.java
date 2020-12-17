package br.com.juliano.appclient.service.impl;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.juliano.appclient.model.Pessoa;
import br.com.juliano.appclient.repository.PessoaRepository;
import br.com.juliano.appclient.repository.to.PessoaInputFilterTO;
import br.com.juliano.appclient.repository.to.PessoaWithCountOutputFilter;
import br.com.juliano.appclient.service.PessoaService;
import br.com.juliano.appclient.structure.exception.notfound.PessoaNotFoundException;
import br.com.juliano.appclient.structure.props.MessagesProp;
import br.com.juliano.appclient.structure.validation.model.PessoaValidation;
import br.com.juliano.appclient.structure.validation.model.PessoaValidation.RULE;

@Service
@Validated
public class PessoaServiceImpl implements PessoaService {
	
	@Autowired
	private PessoaRepository pessoaDao;

	//nunca devolvo um pojo da camada de controller para nao gerar dependencia
	//eh a camada do controller que depende do servico, nunca ao contrario
	//por esse motivo tb os mappers estao na camada do controller
	//alem disso, qualquer solicacao ao banco (via proxy ou nao) deve ser feito na camaca de servico ou dao
	//por isso, no application.yml tem o spring.jpa.open-in-view: false
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class) //rollback na trasacao caso haja qualquer exception
	public Pessoa insertDefault(@PessoaValidation(rule = RULE.INSERT_DEFAULT) Pessoa pes) {
		return pessoaDao.save(pes);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Pessoa updateDefault(@PessoaValidation(rule = RULE.UPDATE_DEFAULT) Pessoa pes) {
		Pessoa old = pessoaDao.findById(pes.getPesnid()).orElseThrow(PessoaNotFoundException::new);
		
		old.setPescnome(pes.getPescnome());
		
		return pessoaDao.save(old); //nao precisa pq 'old' jah estah atachado no contexto
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void deleteById(@NotNull(message = MessagesProp.PESSOA_ID_NOT_NULL) Integer pesnid) {
		pessoaDao.deleteById(pesnid); //aqui vc pode checar se pessoa existe da mesma forma do 'updateDefault' ou pode dar o comando de delete e caso ninguem seja excluido, ignorar
	}
	
	//sim, o PessoaInputFilterTO estah na camada de query para evitar acoplamento com a camada de controller
	//o que possibilita que ele seja usado por qualquer parte do sistema mesmo que haja separacao de camada -> modulos no futuro
	//se ele ficasse na camada de controller isso nao seria possivel
	@Override
	public Page<Pessoa> filterWithSpringCount(PessoaInputFilterTO filter) {
		return pessoaDao.filterWithSpringCount(
				filter.getPesnid(), 
				filter.getPescnome(), 
				filter.getPescemail(), 
				filter.getQuickSearch(), 
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(), 
						Direction.fromOptionalString(filter.getSortDirection()).orElse(Direction.ASC), 
						Objects.requireNonNullElse(filter.getSortField(), "pesnid")
						)
				);
	}
	
	@Override
	public List<Pessoa> filterWithoutCount(PessoaInputFilterTO filter) {
		return pessoaDao.filterWithSpringCount(
				filter.getPesnid(), 
				filter.getPescnome(), 
				filter.getPescemail(), 
				filter.getQuickSearch(), 
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(), 
						Direction.fromOptionalString(filter.getSortDirection()).orElse(Direction.ASC), 
						Objects.requireNonNullElse(filter.getSortField(), "pesnid")
						)
				).getContent();
	}
	
	@Override
	public List<PessoaWithCountOutputFilter> filterWithNativeWindowCount(PessoaInputFilterTO filter) {
		return pessoaDao.filterWithNativeWindowCount(
				filter.getPesnid(), 
				filter.getPescnome(), 
				filter.getPescemail(), 
				filter.getQuickSearch(), 
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(), 
						Direction.fromOptionalString(filter.getSortDirection()).orElse(Direction.ASC), 
						Objects.requireNonNullElse(filter.getSortField(), "pesnid")
						)
				).getContent();
	}
}
