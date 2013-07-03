package org.intermine.webservice.client;

import org.intermine.webservice.client.request.ParameterizedRequest;

public interface Session {
	
	public void authenticate(ParameterizedRequest request);
}
