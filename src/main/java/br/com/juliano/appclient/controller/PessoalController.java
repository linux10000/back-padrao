package br.com.juliano.appclient.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliano.appclient.controller.mapper.PessoaMapper;
import br.com.juliano.appclient.controller.request.PessoaEditRequest;
import br.com.juliano.appclient.controller.request.PessoaInsertRequest;
import br.com.juliano.appclient.controller.response.PessoaDefaultResponse;
import br.com.juliano.appclient.controller.response.PessoaFilterPaginationResponse;
import br.com.juliano.appclient.repository.to.PessoaInputFilterTO;
import br.com.juliano.appclient.repository.to.PessoaWithCountOutputFilter;
import br.com.juliano.appclient.rest.facade.CachorroFacade;
import br.com.juliano.appclient.service.PessoaService;
import br.com.juliano.appclient.service.to.CachorroTO;
import br.com.juliano.appclient.structure.props.MessagesProp;

@RestController
@RequestMapping("/rest/pessoa")
@Validated
public class PessoalController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaMapper pessoaMapper;
	
	@Autowired
	private CachorroFacade cachorroFacade; 

	
	@PostMapping
	public PessoaDefaultResponse insert(@Valid @RequestBody PessoaInsertRequest body) {
		return pessoaMapper.toDefaultResponse(pessoaService.insertDefault(pessoaMapper.to(body)));
	}

	@PutMapping
	public PessoaDefaultResponse update(@Valid @RequestBody PessoaEditRequest body) {
		return pessoaMapper.toDefaultResponse(pessoaService.updateDefault(pessoaMapper.to(body)));
	}
	
	@DeleteMapping
	public void deleteById(@NotNull(message = MessagesProp.PESSOA_ID_NOT_NULL) Integer pesnid) {
		pessoaService.deleteById(pesnid);
	}
	
	@PostMapping("/filter-with-spring-count")
	public PessoaFilterPaginationResponse filterWithSpringCount(@RequestBody @Valid PessoaInputFilterTO filter) {
		return pessoaMapper.toFilterResponse(pessoaService.filterWithSpringCount(filter));
	}
	
	//repare no log que vai fazer apenas uma consulta (e nao duas para contar os registros)
	//mas eh uma consulta nativa com funcao de janela, que eh mais performatico do que fazer 2 consultas
	@PostMapping("/filter-with-native-window-count")
	public List<PessoaWithCountOutputFilter> filterWithNativeWindowCount(@RequestBody @Valid PessoaInputFilterTO filter) {
		return pessoaService.filterWithNativeWindowCount(filter);
	}
	
	//repare no log que vai fazer apenas uma consulta (e nao duas para contar os registros)
	//repare q a primeira chamada eh lenta pq ha uma chamada rest para cada iteracao para trazer o cachorro, mas as proximas chamadas sao rapidas pq as respostas foram armazenadas em cache
	@PostMapping("/filter-without-count")
	public List<PessoaDefaultResponse> filterWithoutCount(@RequestBody @Valid PessoaInputFilterTO filter) {
		return pessoaService.filterWithoutCount(filter).stream()
				.map(pessoaMapper::toDefaultResponse)
				.map(v -> v.setCachorro(cachorroFacade.getByPessoaId(v.getId())))
				.collect(Collectors.toList());
	}
	
	//repare q o cache eh global independente de quem o aqueceu
	//tente buscar pelo mesmo id de uma das ocorrencias do metodo 'filterWithoutCount' e teras o mesmo retorno do cache, independentemente que o retorno do rest seja aleatorio
	@GetMapping("/get-dog")
	public CachorroTO listDogs(@NotNull(message = MessagesProp.PESSOA_ID_NOT_NULL) @RequestParam(value = "pessoaId", required = false) Integer pesnid) {
		return cachorroFacade.getByPessoaId(pesnid);
	}
}
