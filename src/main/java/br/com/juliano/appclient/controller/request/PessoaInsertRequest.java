package br.com.juliano.appclient.controller.request;

import javax.validation.constraints.NotEmpty;
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
public class PessoaInsertRequest {

	@NotEmpty(message = MessagesProp.PESSOA_NOT_NULL)
	@Size(message = MessagesProp.PESSOA_NOME_SIZE_NOT_IN_VALID, max = FieldLength.DESCRICAO_100)
	private String nome;
	
	@NotEmpty(message = MessagesProp.PESSOA_EMAIL_NOT_NULL)
	@Size(message = MessagesProp.PESSOA_EMAIL_SIZE_NOT_VALID, max = FieldLength.DESCRICAO_500)
	private String email;
}


//{
//    "nome":"xuxa23",
//    "email": "xuxa@angelica.com"
//}
