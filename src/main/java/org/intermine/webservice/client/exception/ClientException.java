package org.intermine.webservice.client.exception;

import java.io.IOException;

public class ClientException extends IOException {

	private static final long serialVersionUID = -2274649262681598259L;

	public ClientException(String message) {
		super(message);
	}
	
	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
