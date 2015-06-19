package dishcloth.engine.io.file.dishdata.headers;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * IHeaderDefinition.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * DishData Header is a datablock that has a name (called 'key') and can contain multiple child headers ('subKeys').
 * It is predefined what subkeys each header can have. This is done for increasing parsing performance and keeping
 * file structure simple by enforcing each file to have only one task.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 13.6.2015
 */

public interface IHeaderDefinition {
	String getKey();
	String[] getCompatibleSubkeys();
	boolean isSubKey(String subkey);
}
