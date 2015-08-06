package dishcloth.modules;

import dishcloth.api.IDishclothModule;

/**
 * <b>Program</b>
 * <p>
 * Main -class for DishclothModules
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 5.8.2015 at 23:57
 */

public class Program {
	public static void main(String[] args) {

		ModuleFinder finder = new ModuleFinder( "./modules/" );

		ModuleLoader loader = new ModuleLoader();
		IDishclothModule[] modules = loader.loadModules( finder.findModules() );

		for (IDishclothModule module : modules) {
			module.run( "\n\t\tHello from main!" );
		}
	}
}
