package org.intermine.webservice.client.request;

import org.apache.http.client.ResponseHandler;
import org.intermine.pathquery.PathQuery;
import org.intermine.webservice.client.handler.CountHandler;

public class CountRequest extends QueryRequest implements ExecutableRequest<Integer> {

	public CountRequest(PathQuery query) {
		super(query);
	}

	public String getAccepts() {
		return "application/json;type=count";
	}

	public ResponseHandler<Integer> getResponseHandler() {
		return new CountHandler();
	}

}
