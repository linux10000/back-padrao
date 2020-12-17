package br.com.juliano.appclient.structure.exception.notfound;

import br.com.juliano.appclient.structure.exception.JulianoRuntimeException;

public class PessoaTelefoneNotFoundException extends JulianoRuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String _MSG = "{pessoatelefone.not.found}";

	public PessoaTelefoneNotFoundException() {
		super(_MSG);
	}
}
