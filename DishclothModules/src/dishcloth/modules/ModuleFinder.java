package dishcloth.modules;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>ModuleFinder</b>
 * <p>
 * Detects module .jars in given path.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 2:13
 */

public class ModuleFinder {
	private final File path;

	public ModuleFinder(String path) {
		this.path = new File( path );
		if (!this.path.isDirectory()) {
			System.err.println( "Invalid module finder path! Given path represents a file, not a directory!" );
		}
	}

	public List<URL> findModules() {
		// Prepare a list for results.
		List<URL> jars = new ArrayList<>();

		// Get all files in target path
		File[] files = this.path.listFiles();

		// Validate that we got any results
		if (files != null) {

			// Iterate trough all files
			for (File file : files) {

				// If file expressed by File instance is in fact a file and has file extension '.jar'
				// we'll assume its a valid module. More proper validation is done when URLClassLoader
				// gets to try to load module.info -resource.
				if (file.isFile()
						&& file.getName().substring( file.getName().lastIndexOf( '.' ) ).equalsIgnoreCase( ".jar" )) {

					// Try to add file converted to URI converted to URL into result-list.
					try {
						jars.add( file.toURI().toURL() );
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		// Convert resulting list into an array.
		return jars;
	}
}
