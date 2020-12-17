package br.com.juliano.appclient.structure.exception.business;

import br.com.juliano.appclient.structure.exception.JulianoRuntimeException;

public class InvalidOrderByColumnException extends JulianoRuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidOrderByColumnException() {
		super("Invalid order by column");
	}

}
