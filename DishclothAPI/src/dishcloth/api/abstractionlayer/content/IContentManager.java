package dishcloth.api.abstractionlayer.content;

/**
 * <b>IContentManager</b>
 * <p>
 * Abstracted content manager.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 12:21
 */
public interface IContentManager {
	<T extends IContent> T loadContent(String filename);
}
