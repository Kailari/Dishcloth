package dishcloth.engine.content;

/**
 * AContentImporter.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 26.7.2015
 */

public abstract class AContentImporter<TOutput> {
	public abstract TOutput read(String filename);
}
