package org.intermine.webservice.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;

import org.intermine.metadata.Model;
import org.intermine.pathquery.PathQuery;
import org.intermine.webservice.client.request.CountRequest;
import org.intermine.webservice.client.request.ExecutableRequest;
import org.intermine.webservice.client.util.Functions;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class ServiceTest {

	private Service service;
	

	@Before
	public void setup() throws MalformedURLException {
		URL root = new URL("http://bc.sysbiol.private.cam.ac.uk:8081/intermine-test-dev/service");
		this.service = new Service(root);
	}

	@Test
	public void getSession() {
		Session s = service.connect();
		assertNotNull(s);
	}

	@Test
	public void getVersion() throws Exception {
		int v = service.getVersion().get();
		assertTrue(v + " should be above 0", v > 0);
	}
	
	@Test
	public void getCount() throws Exception {
		final Session s = service.connect();
		
		Future<Model> modelF = service.getModel();
		Future<PathQuery> pqf = Futures.transform(modelF, Functions.TO_QUERY);
		Future<ExecutableRequest<Integer>> reqf = Futures.transform(pqf, new Function<PathQuery, ExecutableRequest<Integer>>() {
			public CountRequest apply(PathQuery query) {
				query.addView("Employee.name");
				return new CountRequest(query);
			}
		});
		ListenableFuture<Integer> countF = Futures.chain(
			Futures.makeListenable(reqf),
			new Function<ExecutableRequest<Integer>, ListenableFuture<Integer>>() {
				public ListenableFuture<Integer> apply(final ExecutableRequest<Integer> req) {
					return service.getQueryResults(s, req);
				}
			}
		);
		int count = countF.get();
		assertTrue("Should be 132 employees, was " + count, count == 132);
	}

}
