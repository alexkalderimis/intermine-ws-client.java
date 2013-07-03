package org.intermine.webservice.client.util;

import org.intermine.metadata.Model;
import org.intermine.pathquery.PathQuery;

import com.google.common.base.Function;

public class Functions {

	private Functions() {
		// Non-instantiable.
	}
	
	public static final Function<? super Model, ? extends PathQuery> TO_QUERY = new Function<Model, PathQuery>() {
		public PathQuery apply(Model model) {
			return new PathQuery(model);
		}
	};
}
