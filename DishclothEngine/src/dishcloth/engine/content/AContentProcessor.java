package dishcloth.engine.content;

/**
 * AContentProcessor.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 26.7.2015
 */

public abstract class AContentProcessor<TInput, TOutput extends AContent> {

	public abstract TOutput process(TInput read, ContentManager contentManager);
}
