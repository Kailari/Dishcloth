package dishcloth.engine.io.file.dishdata.nodes;

import dishcloth.engine.io.file.dishdata.headers.IHeaderDefinition;
import dishcloth.engine.util.logger.Debug;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * HierarchyNode.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 19.6.2015
 */

public class HierarchyNode extends Node<Node> {
	
	public HierarchyNode(IHeaderDefinition headerDefinition) {
		super( headerDefinition );
	}
	
	@Override
	public void setData(String key, Node data) {
		if (validateSubKey( key )) {
			super.setData( key, data );
		}
	}
	
	@Override
	public Node getData(String key) {
		if (validateSubKey( key )) {
			return super.getData( key );
		} else {
			return null;
		}
	}
	
	private boolean validateSubKey(String key) {
		if (getNodeHeader().isSubKey( key )) {
			return true;
		} else {
			Debug.logErr( "Invalid subKey for node \"" + getNodeHeader().getKey() + "\"", this );
			return false;
		}
	}
}
