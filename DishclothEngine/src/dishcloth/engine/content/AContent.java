package dishcloth.engine.content;

import dishcloth.engine.ADishclothObject;

/**
 * <b>AContent</b>
 * <p>
 * Abstract base-class for all content-classes.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 4.8.2015
 *
 * @see ContentPipeline
 * @see ContentManager
 */

// XXX: Must be abstract class due to ADishclothObject.dispose()
public abstract class AContent extends ADishclothObject {
	public AContent() {
		super( false );
	}
}
