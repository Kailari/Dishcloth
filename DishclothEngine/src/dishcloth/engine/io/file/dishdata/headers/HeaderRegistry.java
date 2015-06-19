package dishcloth.engine.io.file.dishdata.headers;

import java.util.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * HeaderRegistry.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 19.6.2015
 */

public class HeaderRegistry {

	private static List<IHeaderDefinition> headers = new ArrayList<>();
	private static HashMap<String, Integer> headerKeys = new HashMap<>();

	public static void registerHeaders(IHeaderDefinition[] headerDefinitions) {
		for (IHeaderDefinition headerDefinition : headerDefinitions) {
			// as headers are never removed from the list, we can use size as index.
			int index = headers.size();
			headers.add( headerDefinition );
			headerKeys.put( headerDefinition.getKey(), index );
		}
	}

	public static IHeaderDefinition getHeader(String key) {
		return headers.get( headerKeys.get( key ) );
	}
}
