package dishcloth.engine.world.block;

import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.SpriteBatch;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * IBlock.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * <p>
 * <p>
 * SUMMARY OF BLOCK SIZE ON THE DISK:
 * <p>
 * Each block consists of following data on the disk:
 * - ID:           1 short     2 bytes
 * - pos:                      2 bytes
 * - size:                     2 bytes
 * - data:                     2 bytes
 * _________________________________________________
 * total:                      8 bytes
 * <p>
 * One chunk is 256 x 256, thus maximum size of a chunk is:
 * 2^8 x 2^8 x [sizeof Block] = ~524 kilobytes
 * <p>
 * <p>
 * data contains:
 * - red color:            4 bits
 * - green color:          4 bits
 * - blue color:           4 bits
 * - brightness:           4 bits
 * ________________________________
 * total:                  2 bytes
 * <p>
 * that leaves space for 2 bytes of block-specific user-defined data
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 7.6.2015
 */
interface IBlock {

	/**
	 * @return block's BlockID
	 */
	BlockID getBlockID();

	/**
	 * @return ID of the frame on the block tileset to render.
	 */
	int getFrameID(); // Not stored on disk, can be used for "animating" blocks

	void render(IRenderer renderer, SpriteBatch spriteBatch);
}
