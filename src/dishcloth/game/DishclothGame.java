package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.io.input.InputAction;
import dishcloth.engine.io.input.InputEvent;
import dishcloth.engine.io.input.InputHandler;
import dishcloth.engine.io.input.events.KeyInputEvent;
import dishcloth.engine.io.input.events.KeyInputRepeatEvent;
import dishcloth.engine.rendering.IRenderer;
import dishcloth.engine.rendering.render2d.Anchor;
import dishcloth.engine.rendering.render2d.Sprite;
import dishcloth.engine.rendering.render2d.SpriteBatch;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.textures.Texture;
import dishcloth.engine.util.Color;
import dishcloth.engine.util.geom.Point;
import org.lwjgl.glfw.GLFW;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * DishclothGame.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class DishclothGame extends AGame {

	SpriteBatch spriteBatch;
	Sprite sprite, sprite2, overlay;

	KeyInputEvent leftArrowRepeatEvent;
	KeyInputEvent rightArrowRepeatEvent;
	KeyInputEvent upArrowRepeatEvent;
	KeyInputEvent downArrowRepeatEvent;

	Point position;
	float t, angle;

	@Override
	public void initialize() {
		position = new Point( 0, 0 );

		leftArrowRepeatEvent = new KeyInputRepeatEvent( getWindowID(), GLFW.GLFW_KEY_LEFT );
		rightArrowRepeatEvent = new KeyInputRepeatEvent( getWindowID(), GLFW.GLFW_KEY_RIGHT );
		upArrowRepeatEvent = new KeyInputRepeatEvent( getWindowID(), GLFW.GLFW_KEY_UP );
		downArrowRepeatEvent = new KeyInputRepeatEvent( getWindowID(), GLFW.GLFW_KEY_DOWN );

		// TODO: Make registerEvent() return the event, so that you could call event = createEvent("eventName")
		//          This would collapse both event = new Event() and Handler.registerEvent() lines on a single line.
		InputHandler.registerEvent( leftArrowRepeatEvent, "leftEvent" );
		InputHandler.registerEvent( rightArrowRepeatEvent, "rightEvent" );
		InputHandler.registerEvent( upArrowRepeatEvent, "upEvent" );
		InputHandler.registerEvent( downArrowRepeatEvent, "downEvent" );

		// YE OLDE WAY OF DOING IT
		InputHandler.bindAction( leftArrowRepeatEvent, new InputAction() {
			@Override
			public void trigger(InputEvent eventTrigger) {
				Point p = getViewportCamera().getPosition();
				getViewportCamera().setPosition( new Point( p.x - 1f, p.y ) );
			}
		} );

		// THE _NEW_ WAY. Lambdas. yay!
		// (You can just write it the old way and press alt+enter when it highlights a warning)
		InputHandler.bindAction( rightArrowRepeatEvent, eventTrigger -> {
			Point p = getViewportCamera().getPosition();
			getViewportCamera().setPosition( new Point( p.x + 1f, p.y ) );
		} );

		InputHandler.bindAction( upArrowRepeatEvent, eventTrigger -> {
			Point p = getViewportCamera().getPosition();
			getViewportCamera().setPosition( new Point( p.x, p.y + 1f ) );
		} );

		InputHandler.bindAction( downArrowRepeatEvent, eventTrigger -> {
			Point p = getViewportCamera().getPosition();
			getViewportCamera().setPosition( new Point( p.x, p.y - 1f ) );
		} );
	}

	@Override
	public void loadContent() {
		spriteBatch = new SpriteBatch( new ShaderProgram( "sprite", "default" ),
		                               getViewportCamera() );

		Texture uvGrid = new Texture( "/textures/debug/uv_checker.png" );
		sprite = new Sprite( uvGrid, 8, 8, 0, Anchor.CENTER );
		sprite2 = new Sprite( uvGrid, 1, 1, 0 );

		overlay = new Sprite( new Texture( "/textures/debug/800x600.png" ), 1, 1, 0 );
	}

	@Override
	public void update(float delta) {
		t += delta;

		sprite.setFrame( Math.round( t ) );

		position.x = (float) Math.cos( Math.toRadians( angle ) ) * 200f;
		position.y = (float) Math.sin( Math.toRadians( angle ) ) * 200f;

		angle = t * (360f / 10f);
	}

	@Override
	public void fixedUpdate() {

	}

	@Override
	public void render(IRenderer renderer) {

		sprite2.render( spriteBatch, new Point( -512f, 512f ), -angle, Color.WHITE, new Point( 512f, -512f ) );

		// "sprite" uses default origin set as "Anchor.CENTER", thus no pivot parameter is needed
		sprite.render( spriteBatch, new Point( -64 + position.x, 64 + position.y ), 0f, Color.CYAN );
		sprite.render( spriteBatch, new Point( -64 - position.x, 64 - position.y ), -angle, Color.GREEN );

		sprite.render( spriteBatch, new Point( -64f, 64f ), angle );

		// "overlay" has default pivot "TOPLEFT" and uses manual pivot (400, -300)
		overlay.render( spriteBatch, new Point( -400f, 300 ), 0f, Color.WHITE, new Point( 400, -300 ) );

		spriteBatch.render( renderer );
	}

	@Override
	public void unloadContent() {
		sprite.dispose();   // Both sprites use the same texture, dispose only one of them
		overlay.dispose();
		spriteBatch.dispose();
	}

	@Override
	public void shutdown() {

	}
}
