package org.intermine.webservice.client.request;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;

public abstract class BaseRequest {

	Set<NameValuePair> parameters = new HashSet<NameValuePair>();
	Set<NameValuePair> headers = new HashSet<NameValuePair>();
	
	public void addParameter(String name, String value) {
		parameters.add(new Pair(name, value));
	}
	
	public void addHeader(String name, String value) {
		parameters.add(new Pair(name, value));
	}
	
	public Iterable<? extends NameValuePair> getParameters() {
		return parameters;
	}
	
	public Header[] getHeaders() {
		int size = headers.size();
		Iterator<NameValuePair> it = headers.iterator();
		Header[] hs = new Header[size];
		for (int i = size; it.hasNext(); i++) {
			NameValuePair pair = it.next();
			hs[i] = new BasicHeader(pair.getName(), pair.getValue());
		}
		return hs;
	}
	
	static class Pair implements NameValuePair {

		private String name, value;
		
		Pair(String name, String value) {
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
		
	}
}
