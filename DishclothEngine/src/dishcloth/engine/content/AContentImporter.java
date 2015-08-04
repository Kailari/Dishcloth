package dishcloth.engine.content;

/**
 * <b>AContentImporter</b>
 * <p>
 * Abstract base for content importers
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 26.7.2015
 *
 * @param <TOutput> Type of data this importer outputs.
 */

public abstract class AContentImporter<TOutput> {
	public abstract TOutput read(String filename);
}
