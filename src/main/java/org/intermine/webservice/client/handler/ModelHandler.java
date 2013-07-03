package org.intermine.webservice.client.handler;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.intermine.metadata.Model;
import org.intermine.modelproduction.ModelParserException;
import org.intermine.modelproduction.xml.InterMineModelParser;

public class ModelHandler implements ResponseHandler<Model> {

	public Model handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		InterMineModelParser mp = new InterMineModelParser();
		Model m;
		try {
			m = mp.process(new InputStreamReader(response.getEntity().getContent()));
			m.setGeneratedClassesAvailable(false);
		} catch (IllegalStateException e) {
			throw new IOException(e);
		} catch (ModelParserException e) {
			throw new IOException(e);
		}
		return m;
	}

}
