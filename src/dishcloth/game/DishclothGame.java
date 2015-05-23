package dishcloth.game;

import dishcloth.engine.AGame;
import dishcloth.engine.rendering.Renderer;
import dishcloth.engine.rendering.shaders.ShaderProgram;
import dishcloth.engine.rendering.vbo.Vertex;
import dishcloth.engine.rendering.vbo.VertexBufferObject;
import dishcloth.engine.rendering.vbo.shapes.Polygon;
import dishcloth.engine.rendering.vbo.shapes.RegularNGon;
import dishcloth.engine.util.Color;

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

	ShaderProgram shaderProgram;
	VertexBufferObject vbo;

	@Override
	public void initialize() {

	}

	@Override
	public void loadContent() {
		shaderProgram = new ShaderProgram( "default", "default" );

		Polygon p = new RegularNGon( 4, 1f );
		p.setVertexColor( 0, Color.RED );
		p.setVertexColor( 1, Color.GREEN );
		p.setVertexColor( 2, Color.BLUE );
		p.setVertexColor( 3, Color.MAGENTA );

		vbo = new VertexBufferObject( p );

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void fixedUpdate() {

	}

	@Override
	public void render(Renderer renderer) {

		shaderProgram.bind();

		vbo.render();

		shaderProgram.unbind();
	}

	@Override
	public void unloadContent() {
		shaderProgram.dispose();

		vbo.dispose();
	}

	@Override
	public void shutdown() {

	}
}
