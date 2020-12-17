package br.com.juliano.appclient.controller.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class PessoaFilterPaginationResponse extends BaseFilterResponse<PessoaDefaultResponse> {
	
}


