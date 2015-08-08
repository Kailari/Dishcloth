package dishcloth.game;

import dishcloth.engine.util.BitUtils;
import dishcloth.engine.util.debug.Debug;
import dishcloth.engine.world.level.database.TerrainTree;

import java.io.File;
import java.lang.reflect.Field;

public class Program {

	public static void main(String[] args) {
		launchDishclothGame();
	}

	public static void launchDishclothGame() {
		System.out.println( "Application is starting..." );

		setLibsPath( "../libs/natives/" );
		new DishclothGame().run();

		System.out.println( "Application stopped." );
	}

	public static void setLibsPath(String s) {
		try {

			Field field = ClassLoader.class.getDeclaredField( "usr_paths" );
			field.setAccessible( true );
			String[] paths = (String[]) field.get( null );
			for (String path : paths) {
				if (s.equals( path )) {
					return;
				}
			}
			String[] tmp = new String[paths.length + 1];
			System.arraycopy( paths, 0, tmp, 0, paths.length );
			tmp[paths.length] = s;
			field.set( null, tmp );
			System.setProperty( "java.library.path", System.getProperty( "java.library.path" ) + File.pathSeparator + s );
		} catch (IllegalAccessException | NoSuchFieldException e) {
			Debug.logException( e, "Program" );
		}
	}
}
