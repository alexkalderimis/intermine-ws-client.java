package org.intermine.webservice.client;

import org.intermine.webservice.client.request.ParameterizedRequest;

public class AnonymousSession implements Session {

	private Service service;

	public AnonymousSession(Service service) {
		this.service = service;
	}
	
	public void authenticate(ParameterizedRequest request) {
		// Do nothing.
	}

}
