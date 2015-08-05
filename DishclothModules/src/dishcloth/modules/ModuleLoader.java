package dishcloth.modules;

import dishcloth.api.IDishclothModule;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * <b>ModuleLoader</b>
 * <p>
 * Loads modules from given .jar -files
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 0:25
 */

public class ModuleLoader {
	/**
	 * Loads a module main-class from .jar and instantiates it.
	 *
	 * @param moduleName      Name of module .jar
	 * @param moduleMainClass Full qualified name of the module main class
	 * @return new instance of the module main.
	 */
	public IDishclothModule loadModule(String moduleName, String moduleMainClass) {
		URLClassLoader urlClassLoader = new URLClassLoader( createURLsFromModuleName( moduleName ) );

		try {
			Class moduleClass = urlClassLoader.loadClass( moduleMainClass );
			Object o = moduleClass.newInstance();
			return (IDishclothModule) o;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	private URL[] createURLsFromModuleName(String moduleName) {
		URL[] result = new URL[1];
		try {
			File f = new File( "./modules/" + moduleName + ".jar" );
			if (!f.isFile()) {
				System.out.println("File not found!");
			}
			result[0] = f.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return result;
	}
}
