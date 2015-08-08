package dishcloth.test;

import dishcloth.api.GameInfo;
import dishcloth.api.abstractionlayer.content.IContentManager;
import dishcloth.api.abstractionlayer.events.EventHandler;
import dishcloth.api.abstractionlayer.rendering.IRenderer;
import dishcloth.api.modules.DishclothModuleBase;
import dishcloth.api.util.logger.APIDebug;
import dishcloth.test.blocks.TestModuleBlocks;

/**
 * <b>dishcloth.test.TestModule</b>
 * <p>
 * Module-main for the test-module.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 0:06
 */

public class TestModule extends DishclothModuleBase {
	private boolean flag;

	@Override
	public void initialize() {
		APIDebug.logOK( "Hello from TestModule!", this );

		APIDebug.logNote( "Registering blocks in TestModule", this );
		TestModuleBlocks.registerBlocks();
	}

	@Override
	public void loadContent(IContentManager contentManager) {

	}

	@Override
	protected void unloadContent(IContentManager contentManager) {

	}

	@Override
	protected void shutdown() {

	}


	@Override
	protected void update() {
		if (!flag) {
			flag = true;

			//GameInfo.worldInfo.terrain.setBlock( 0, 0, TestModuleBlocks.TRIPPY );
		}
	}

	@Override
	protected void fixedUpdate() {

	}

	@Override
	protected void render(IRenderer renderer) {

	}
}
