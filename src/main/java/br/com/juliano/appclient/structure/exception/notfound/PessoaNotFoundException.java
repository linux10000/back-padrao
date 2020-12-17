package br.com.juliano.appclient.structure.exception.notfound;

import br.com.juliano.appclient.structure.exception.JulianoRuntimeException;

public class PessoaNotFoundException extends JulianoRuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String _MSG = "{pessoa.not.found}";

	public PessoaNotFoundException() {
		super(_MSG);
	}
}
