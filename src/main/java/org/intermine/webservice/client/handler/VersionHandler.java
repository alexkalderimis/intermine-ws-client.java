package org.intermine.webservice.client.handler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

public class VersionHandler implements ResponseHandler<Integer> {

	private BasicResponseHandler brh = new BasicResponseHandler();
	
	public Integer handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		String content = brh.handleResponse(response); 
		return Integer.valueOf(content.trim());
	}
}