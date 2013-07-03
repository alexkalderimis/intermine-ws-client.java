package org.intermine.webservice.client.request;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;

public interface ExecutableRequest<T> extends ParameterizedRequest {
	
	public Iterable<? extends NameValuePair> getParameters();
	
	public Header[] getHeaders();
	
	public String getAccepts();
	
	public ResponseHandler<T> getResponseHandler();
}
