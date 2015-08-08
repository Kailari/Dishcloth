package dishcloth.engine.world.block;

import dishcloth.api.abstractionlayer.world.block.IBlockID;
import dishcloth.api.world.block.ABlock;
import dishcloth.api.util.geom.Point;
import dishcloth.api.util.memory.PointCache;
import dishcloth.api.util.memory.RectangleCache;
import dishcloth.engine.rendering.render2d.sprites.batch.SpriteBatch;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.api.util.geom.Rectangle;
import dishcloth.engine.util.debug.Debug;
import dishcloth.api.abstractionlayer.world.level.ITile;
import dishcloth.engine.world.level.TerrainChunk;

import java.lang.reflect.Field;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockHelper.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 11.6.2015
 */

public class BlockHelper {

	private static Rectangle source = RectangleCache.getRectangle( 0, 0, 0, 0 );
	private static Rectangle destination = RectangleCache.getRectangle( 0, 0, 0, 0 );
	private static Point ORIGIN = PointCache.getPoint( 0, 0 );

	public static void setBlockID(ABlock block, IBlockID id) {
		try {
			Field field = ABlock.class.getDeclaredField( "blockID" );
			field.setAccessible( true );
			field.set( block, id );
			field.setAccessible( false );
		} catch (NoSuchFieldException | IllegalAccessException e) {
			Debug.logException( e, "BlockHelper" );
		}
	}

	public static void setFrameID(ABlock block, int id) {
		try {
			Field field = ABlock.class.getDeclaredField( "frameID" );
			field.setAccessible( true );
			field.set( block, id );
			field.setAccessible( false );
		} catch (NoSuchFieldException | IllegalAccessException e) {
			Debug.logException( e, "BlockHelper" );
		}
	}

	public static void renderBlock(SpriteBatch spriteBatch, ITile tile, int chunkX, int chunkY) {
		if (tile.getBlockID() == null) {
			return;
		}

		Texture texture = BlockTextureAtlas.getTexture();

		int atlasSize = Math.round( texture.getWidth() / TerrainChunk.BLOCK_SIZE );

		int frameID = BlockRegistry.getBlock( tile.getBlockID() ).getFrameID();
		int row = (int) Math.floor( (float) frameID / (float) atlasSize );
		int column = frameID % atlasSize;

		source.setW( tile.getSize() * texture.getWidth() );
		source.setH( tile.getSize() * texture.getHeight() );

		if (chunkX < 0) {
			int foo = 0;
		}

		destination.setX( (chunkX * TerrainChunk.CHUNK_SIZE + tile.getX()) * TerrainChunk.BLOCK_SIZE );
		destination.setY( (chunkY * TerrainChunk.CHUNK_SIZE + tile.getY()) * TerrainChunk.BLOCK_SIZE );
		destination.setW( tile.getSize() * TerrainChunk.BLOCK_SIZE );
		destination.setH( tile.getSize() * TerrainChunk.BLOCK_SIZE );

		short u_offs = (short) Math.round( ((float) column / (float) atlasSize) * Short.MAX_VALUE );
		short v_offs = (short) Math.round( ((float) row / (float) atlasSize) * Short.MAX_VALUE );

		int uvOffs = u_offs | (int) v_offs << 16;

		spriteBatch.queue( texture,
		                   destination,
		                   source,
		                   0f,
		                   uvOffs,
		                   ORIGIN );
	}
}
