package org.intermine.webservice.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.client.fluent.Async;
import org.apache.http.client.fluent.Request;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.intermine.metadata.Model;
import org.intermine.webservice.client.handler.ModelHandler;
import org.intermine.webservice.client.handler.VersionHandler;
import org.intermine.webservice.client.request.ExecutableRequest;
import org.intermine.webservice.client.request.ParameterizedRequest;
import org.intermine.webservice.client.request.QueryRequest;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class Service {
	
	private URL root;
	private static ExecutorService threadpool = Executors.newFixedThreadPool(4);
	private static Async async = Async.newInstance().use(threadpool);
	private int version;
	//private DefaultHttpClient client = new DefaultHttpClient();

	private static URI getResourceURI(URL root, String subpath) {
		URI uri;
		try {
			uri = new URI(root + subpath);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Unexpectedly bad URI");
		}
		return uri;
	}
	
	private Service(URL root, int version) {
		this.root = root;
		this.version = version;
	}
	
	public static ListenableFuture<Session> connect(final URL root) {
		Request req = Request.Get(getResourceURI(root, "/version"))
				.connectTimeout(1000).addHeader("Accept", "text/plain");
			return Futures.transform(
					Futures.makeListenable(async.execute(req, new VersionHandler())),
					new Function<Integer, Session>() {
						public Session apply(Integer v) {
							return new AnonymousSession(new Service(root, v));
						}
					}
			);
	}
	
	public Future<Model> getModel() {
		Request req = Request.Get(getResourceURI("/model"))
				.connectTimeout(1000).addHeader("Accept", "application/xml");
		return async.execute(req, new ModelHandler());
	}
	
	public <T> ListenableFuture<T> getQueryResults(Session session, ExecutableRequest<T> er) {
		session.authenticate(er);
		
		Request req = Request.Post(getResourceURI("/query/results"))
				.connectTimeout(1000)
				.setHeaders(er.getHeaders())
				.addHeader("Accept", er.getAccepts())
				.bodyForm(er.getParameters());
		
		return Futures.makeListenable(async.execute(req, er.getResponseHandler()));
	}

}
