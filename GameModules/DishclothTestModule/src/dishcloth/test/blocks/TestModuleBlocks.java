package dishcloth.test.blocks;

import dishcloth.api.world.block.APIBlockRegistry;

/**
 * <b>TestModuleBlocks</b>
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 10:39
 */

public class TestModuleBlocks {
	public static final TestModuleBlock TRIPPY = new TestModuleBlock();

	public static void registerBlocks() {
		APIBlockRegistry.registerBlock( TRIPPY, "TestModule", "trippy" );
	}
}
