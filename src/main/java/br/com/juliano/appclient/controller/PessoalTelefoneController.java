package br.com.juliano.appclient.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliano.appclient.controller.mapper.PessoaTelefoneMapper;
import br.com.juliano.appclient.controller.request.PessoaTelefoneEditRequest;
import br.com.juliano.appclient.controller.request.PessoaTelefoneInsertRequest;
import br.com.juliano.appclient.controller.response.PessoaTelefoneDefaultResponse;
import br.com.juliano.appclient.repository.PessoaTelefoneRepository;
import br.com.juliano.appclient.repository.to.PessoaTelefoneInputFilterTO;
import br.com.juliano.appclient.repository.to.PessoaTelefoneWithPessoaOutputFilter;
import br.com.juliano.appclient.service.PessoaTelefoneService;
import br.com.juliano.appclient.structure.props.MessagesProp;

@RestController
@RequestMapping("/rest/pessoa-telefone")
@Validated
public class PessoalTelefoneController {
	
	@Lazy
	@Autowired
	private PessoaTelefoneRepository pessoaTelefoneDao;
	
	@Lazy
	@Autowired
	private PessoaTelefoneService pessoaTelefoneService;
	
	@Lazy
	@Autowired
	private PessoaTelefoneMapper pessoaTelefoneMapper;

	@PostMapping
	public PessoaTelefoneDefaultResponse insert(@Valid @RequestBody PessoaTelefoneInsertRequest body) {
		return pessoaTelefoneMapper.toDefaultResponse(pessoaTelefoneService.insertDefault(pessoaTelefoneMapper.to(body)));
	}

	@PutMapping
	public PessoaTelefoneDefaultResponse update(@Valid @RequestBody PessoaTelefoneEditRequest body) {
		return pessoaTelefoneMapper.toDefaultResponse(pessoaTelefoneService.updateDefault(pessoaTelefoneMapper.to(body)));
	}
	
	@DeleteMapping
	public void deleteById(@NotNull(message = MessagesProp.PESSOA_TELEFONE_ID_NOT_NULL) Integer ptlnid) {
		pessoaTelefoneService.deleteById(ptlnid);
	}
	
	@PostMapping("/filter-with-pessoa")
	public List<PessoaTelefoneDefaultResponse> filterWithPessoa(@RequestBody @Valid PessoaTelefoneInputFilterTO filter) {
		return pessoaTelefoneService.filterWithPessoa(filter).stream()
				.map(pessoaTelefoneMapper::toDefaultResponse)
				.collect(Collectors.toList());
	}
	
	@PostMapping("/filter-native-with-pessoa")
	public List<PessoaTelefoneWithPessoaOutputFilter> filterWithNativeWindowCount(@RequestBody @Valid PessoaTelefoneInputFilterTO filter) {
		return pessoaTelefoneService.filterWithNativeWithPessoa(filter);
	}
}
