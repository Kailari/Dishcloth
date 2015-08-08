package dishcloth.engine.modules;

import dishcloth.api.abstractionlayer.rendering.IRenderer;
import dishcloth.api.modules.DishclothModuleBase;
import dishcloth.engine.content.ContentManager;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>ModuleManager</b>
 * <p>
 * Manages loaded modules and loads new ones on demand.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 11:33
 */

public class ModuleManager {
	protected static List<DishclothModuleBase> modules= new ArrayList<>();
	protected static List<String> modulePaths = new ArrayList<>(  );

	public static InputStream createInputStream(String path) {
		if (modules != null && modules.size() > 0) {
			return modules.get( 0 ).getClass().getResourceAsStream( path );
		}

		return null;
	}

	/**
	 * Initializes all modules. Automatically flushes module paths for new modules.
	 */
	public static void doModuleInitialization() {
		flushModulePaths();

		modules.forEach( DishclothModuleBase::doInitialize );
	}

	/**
	 * Runs modules' loadContent -methods.
	 */
	public static void loadModuleContent(ContentManager contentManager) {
		modules.forEach( module -> module.doLoadContent( contentManager ) );
	}

	/**
	 * Runs modules' unloadContent -methods.
	 */
	public static void unloadModuleContent(ContentManager contentManager) {
		modules.forEach( module -> module.doUnloadContent( contentManager ) );
	}

	/**
	 * Runs modules' shutdown -methods.
	 */
	public static void shutdownModule() {
		modules.forEach( DishclothModuleBase::doShutdown );
	}

	/**
	 * Runs modules' update -methods.
	 */
	public static void tickModuleUpdate() {
		modules.forEach( DishclothModuleBase::doUpdate );
	}

	/**
	 * Runs modules' fixedUpdate -methods.
	 */
	public static void tickModuleFixedUpdate() {
		modules.forEach( DishclothModuleBase::doFixedUpdate );
	}

	/**
	 * Runs modules' renderBlock -methods
	 */
	public static void doModuleRender(IRenderer renderer) {
		modules.forEach( module -> module.doRender( renderer ) );
	}

	/**
	 * Registers a new <b>folder</b> for searching modules from.
	 * Path validation is automatically done for all registered paths.
	 * <p>
	 * One path can contain multiple modules.
	 *
	 * @param path Path to folder that contains one or more modules.
	 */
	public static void registerModulePath(String path) {
		if (validatePath( path )) {
			modulePaths.add( path );
		}
	}

	/**
	 * Goes trough all registered module paths and finds and loads modules.
	 * <p>
	 * Internal list of module paths is cleared afterwards to prevent from
	 * trying to re-register modules from same paths.
	 */
	public static void flushModulePaths() {
		modulePaths.forEach( path -> findAndLoadModules( path, false ) );
		modulePaths.clear();
	}

	/**
	 * Finds and loads modules from given path.
	 *
	 * @param path Path from where to look for modules
	 */
	public static void findAndLoadModules(String path) {
		findAndLoadModules( path, true );
	}

	/**
	 * Finds and loads modules from given path.
	 *
	 * @param path       Path from where to look for modules
	 * @param doValidate Flags if path should be validated.
	 */
	public static void findAndLoadModules(String path, boolean doValidate) {
		if (doValidate) {
			if (!validatePath( path )) {
				return;
			}
		}
		ModuleFinder finder = new ModuleFinder( path );
		ModuleLoader loader = new ModuleLoader();

		List<URL> moduleURLs = finder.findModules();

		if (moduleURLs.size() > 0) {
			DishclothModuleBase[] loadedModules = loader.loadModules( moduleURLs );

			for (DishclothModuleBase module : loadedModules) {
				if (module != null && !modules.contains( module )) {
					modules.add( module );
				} else {
					System.err.println( "Error while loading modules! Module either was null or it was already loaded." );
				}
			}
		}
	}

	/**
	 * Validates given path.
	 *
	 * @param path Path to validate
	 * @return true if path was valid, false otherwise
	 */
	private static boolean validatePath(String path) {
		// TODO: Implement path validation
		return true;
	}
}
