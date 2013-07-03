package org.intermine.webservice.client.handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.intermine.webservice.client.exception.ClientException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONHandler implements ResponseHandler<JSONObject> {

	BasicResponseHandler brh = new BasicResponseHandler();
	public JSONObject handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		if (response.getStatusLine().getStatusCode() >= 300) {
			throw new ClientException(response.getStatusLine().toString());
		}
		//Reader r = new InputStreamReader(response.getEntity().getContent());
		String text = brh.handleResponse(response);
		try {
			return new JSONObject(text);//new JSONTokener(r));
		} catch (JSONException e) {
			throw new ClientException("Could not parse JSON (" + text + ")", e);
		}
	}

	
}
