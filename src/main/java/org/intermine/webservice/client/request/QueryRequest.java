package org.intermine.webservice.client.request;

import org.intermine.pathquery.PathQuery;

public class QueryRequest extends BaseRequest {

	private PathQuery query;

	public QueryRequest(PathQuery query) {
		this.query = query;
		addParameter("query", query.toXml());
	}

}
