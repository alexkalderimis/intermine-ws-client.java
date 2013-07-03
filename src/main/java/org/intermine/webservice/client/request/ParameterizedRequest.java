package org.intermine.webservice.client.request;


public interface ParameterizedRequest {

	public void addParameter(String name, String value);
	
	public void addHeader(String name, String value);
	
	
}
