package dishcloth.engine.rendering;

import dishcloth.engine.util.logger.Debug;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ShaderProgram.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 21.5.2015
 */

public class ShaderProgram {
	int programID;

	int vertID;
	int fragID;

	public ShaderProgram() {
		programID = glCreateProgram();
	}

	public static String readFromFile(String filename) {

		StringBuilder source = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader( ShaderProgram.class
							                       .getClassLoader()
							                       .getResourceAsStream( filename ) ) );
		} catch (Exception e) {
			Debug.logException( e, "IOHelper" );
		}

		return source.toString();
	}


}
