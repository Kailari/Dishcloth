package dishcloth.api.abstractionlayer.rendering.textures;

/**
 * <b>ITexture</b>
 * <p>
 * Texture abstraction
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 13:53
 */
public interface ITexture {
	int getGLTexID();

	int getWidth();

	int getHeight();
}
