package dishcloth.engine.content;

import dishcloth.engine.content.importers.ReadAllLinesImporter;

/**
 * <b>AContentProcessor</b>
 * <p>
 * Abstract base for content processors. Purpose of content processors is
 * to parse data received from importer into usable content instances.
 * <p>
 * This implementation allows for easy, flexible and reusable content processing
 * sequence, and keeps actual content processing and file I/O out of content
 * classes.
 * <p>
 * Also, notice how TInput isn't strictly bound to any exact content importer,
 * thus allowing multiple processors to use same importer. Example of such
 * importer is {@link ReadAllLinesImporter}
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 26.7.2015
 *
 * @param <TInput>  Type of data this processor receives from the importer.
 * @param <TOutput> Type of content this processor produces.
 */

public abstract class AContentProcessor<TInput, TOutput extends AContent> {

	public abstract TOutput process(TInput read, ContentManager contentManager);
}
