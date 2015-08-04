package dishcloth.engine.content.importers;

import dishcloth.engine.content.AContentImporter;
import dishcloth.engine.io.FileIOHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <b>TextureImporter</b>
 * <p>
 * Reads an image file and returns it as a TextureImportInfo.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 4.8.2015
 *
 * @see dishcloth.engine.content.AContentImporter
 */

public class TextureImporter extends AContentImporter<TextureImporter.TextureImportInfo> {
	@Override
	public TextureImportInfo read(String filename) {
		int[] pixels = null;
		int width = 0;
		int height = 0;
		try {
			BufferedImage image = ImageIO.read( FileIOHelper.createInputStream( filename ) );

			width = image.getWidth();
			height = image.getHeight();

			pixels = new int[width * height];

			image.getRGB( 0, 0, width, height, pixels, 0, width );
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new TextureImportInfo( width, height, pixels );
	}

	public static class TextureImportInfo {
		private final int[] pixels;
		private final int imgWidth;
		private final int imgHeight;

		public TextureImportInfo(int imgWidth, int imgHeight, int[] pixels) {
			this.imgHeight = imgHeight;
			this.imgWidth = imgWidth;
			this.pixels = pixels;
		}

		public int[] getPixels() {
			return pixels;
		}

		public int getImgWidth() {
			return imgWidth;
		}

		public int getImgHeight() {
			return imgHeight;
		}
	}
}
