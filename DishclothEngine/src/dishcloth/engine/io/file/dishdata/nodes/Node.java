package dishcloth.engine.io.file.dishdata.nodes;

import dishcloth.engine.io.file.dishdata.headers.IHeaderDefinition;

import java.util.HashMap;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Node.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 19.6.2015
 */

public class Node<T> {
	private IHeaderDefinition nodeHeader;
	private HashMap<String, T> data;

	public Node(IHeaderDefinition headerDefinition) {
		this.nodeHeader = headerDefinition;
	}

	public IHeaderDefinition getNodeHeader() {
		return nodeHeader;
	}

	public Node() {
		this.data = new HashMap<>();
	}

	public T getData(String key) {
		return data.get( key );
	}

	public void setData(String key, T data) {
		this.data.put( key, data );
	}
}
