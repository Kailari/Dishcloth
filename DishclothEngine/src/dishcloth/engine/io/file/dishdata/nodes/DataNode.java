package dishcloth.engine.io.file.dishdata.nodes;

import dishcloth.engine.io.file.dishdata.headers.IHeaderDefinition;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * DataNode.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 19.6.2015
 */

public class DataNode extends Node<Object> {

	public DataNode(IHeaderDefinition headerDefinition) {
		super( headerDefinition );
	}

	public int getDataAsInt(String key) {
		return (int) this.getData( key );
	}

	public float getDataAsFloat(String key) {
		return (float) this.getData( key );
	}

	public String getDataAsString(String key) {
		return (String) this.getData( key );
	}
}
