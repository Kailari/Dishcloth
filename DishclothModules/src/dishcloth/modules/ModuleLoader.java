package dishcloth.modules;

import dishcloth.api.IDishclothModule;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>ModuleLoader</b>
 * <p>
 * Loads modules from given .jar -files
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 0:25
 */

public class ModuleLoader {

	public IDishclothModule[] loadModules(List<URL> moduleURLs) {
		List<IDishclothModule> modules = new ArrayList<>();

		URL[] urls = new URL[moduleURLs.size()];
		urls = moduleURLs.toArray( urls );

		URLClassLoader urlClassLoader = new URLClassLoader( urls );

		for (URL url : moduleURLs) {

			String filename = url.getFile().replace( "\\", "/" );
			filename = filename.substring( filename.lastIndexOf( '/' ) + 1, filename.lastIndexOf( '.' ) );
			filename += ".info";

			String moduleMainClassName = readModuleMainClassNameFromInfo( urlClassLoader, filename );

			if (moduleMainClassName != null) {
				IDishclothModule module = loadModule( urlClassLoader, moduleMainClassName );
				modules.add( module );
			} else {
				System.err.println( "Encountered error while reading module.info" );
			}
		}

		// Return the result as an array
		IDishclothModule[] result = new IDishclothModule[modules.size()];
		result = modules.toArray( result );
		return result;
	}

	private String readModuleMainClassNameFromInfo(URLClassLoader urlClassLoader, String filename) {
		String result = null;

		InputStream stream = urlClassLoader.getResourceAsStream( filename );

		if (stream != null) {
			try (BufferedReader reader = new BufferedReader( new InputStreamReader( stream ) )) {
				do {
					result = reader.readLine();
				}
				while (reader.ready() && result != null && result.length() == 0);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		return result;
	}

	private IDishclothModule loadModule(URLClassLoader urlClassLoader, String moduleMainClass) {
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
				System.out.println( "File not found!" );
			}
			result[0] = f.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return result;
	}
}
