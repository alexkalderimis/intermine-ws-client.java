package org.intermine.webservice.client.handler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.intermine.webservice.client.exception.ClientException;
import org.json.JSONException;
import org.json.JSONObject;

public class CountHandler implements ResponseHandler<Integer> {

	private JSONHandler jh = new JSONHandler();

	public Integer handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		JSONObject data = jh.handleResponse(response);
		try {
			return data.getInt("count");
		} catch (JSONException e) {
			throw new ClientException("Expected 'count' on results object", e);
		}
	}
	
}
