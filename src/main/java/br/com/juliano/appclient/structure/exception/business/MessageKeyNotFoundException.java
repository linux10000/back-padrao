package br.com.juliano.appclient.structure.exception.business;

import br.com.juliano.appclient.structure.exception.JulianoRuntimeException;

public class MessageKeyNotFoundException extends JulianoRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MessageKeyNotFoundException(String key) {
		super(String.format("Message key not found: %s", key));
	}
}
