package br.com.juliano.appclient.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import br.com.juliano.appclient.structure.constants.FieldLength;
import br.com.juliano.appclient.structure.props.MessagesProp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class PessoaTelefoneEditRequest {

	@NotNull(message = MessagesProp.PESSOA_TELEFONE_ID_NOT_NULL)
	private Integer id;
	
	@NotEmpty(message = MessagesProp.PESSOA_TELEFONE_NUMERO_NOT_NULL)
	@Size(message = MessagesProp.PESSOA_TELEFONE_NUMERO_SIZE_NOT_VALID, max = FieldLength.DESCRICAO_100)
	private String numero;
	
	@NotEmpty(message = MessagesProp.PESSOA_TELEFONE_TIPO_NOT_NULL)
	private String tipo;
}