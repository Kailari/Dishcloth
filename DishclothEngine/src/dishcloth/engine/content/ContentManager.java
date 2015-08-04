package dishcloth.engine.content;

import dishcloth.engine.ADishclothObject;
import java.util.ArrayList;
import java.util.List;

/**
 * ContentManager.java
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 4.8.2015
 */

public class ContentManager extends ADishclothObject {
	protected List<AContent> loadedContent;

	public ContentManager() {
		super( false );
		loadedContent = new ArrayList<>();
	}

	public <T extends AContent> T loadContent(String filename) {
		T content = ContentPipeline.importAndProcessContent( filename, this );
		loadedContent.add( content );

		return content;
	}

	/**
	 * @param content Content to unload
	 * @return true if operation was successful, false if given content was not found.
	 * @throws NullPointerException if content is null
	 */
	public <T extends AContent> boolean unloadContent(T content) {
		return unloadContent( content, true );
	}

	private <T extends AContent> boolean unloadContent(T content, boolean doRemove) {
		if (content == null) {
			throw new NullPointerException();
		}

		// Content is not null, dispose it.
		content.dispose();

		int index = loadedContent.indexOf( content );

		// Content does not exist
		if (index == -1) {
			return false;
		}

		// Remove from list
		if (doRemove) {
			loadedContent.remove( index );
		}
		return true;
	}


	@Override
	public void dispose() {
		// Don't remove from list in order to avoid ConcurrentModificationException
		this.loadedContent.forEach( content -> unloadContent( content, false ) );

		// Now we can clear the list.
		this.loadedContent.clear();
	}
}
