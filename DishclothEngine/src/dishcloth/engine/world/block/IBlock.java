package dishcloth.engine.world.block;

import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.SpriteBatch;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * IBlock.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *
 *
 * SUMMARY OF BLOCK SIZE ON THE DISK:
 * <p>
 * Each block consists of following data on the disk:
 * - ID:           1 short     2 bytes
 * - pos:                      2 bytes
 * - data:                     4 bytes
 * _________________________________________________
 * total:                  8 bytes
 * <p>
 * One chunk is 256 x 256, thus
 * 2^8 x 2^8 x [sizeof Block] = 512 kilobytes
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 7.6.2015
 */
public interface IBlock {

	/**
	 * @return block's BlockID
	 */
	BlockID getBlockID();

	/**
	 * @return x-coordinate relative to the current chunk
	 */
	byte getLocalX();

	/**
	 * @return y-coordinate relative to the current chunk
	 */
	byte getLocalY();

	/**
	 * @return global x-coordinate
	 */
	int getX();

	/**
	 * @return global y-coordinate
	 */
	int getY();

	/**
	 * @return  ID of the frame on the block tileset to render.
	 */
	int getFrameID(); // Not stored on disk, can be used for "animating" blocks

	void render(IRenderer renderer, SpriteBatch spriteBatch);
}
