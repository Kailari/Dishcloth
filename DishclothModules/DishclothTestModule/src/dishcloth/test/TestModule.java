package dishcloth.test;

import dishcloth.api.IDishclothModule;

/**
 * <b>dishcloth.test.TestModule</b>
 * <p>
 * Module-main for the test-module.
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 0:06
 */

public class TestModule implements IDishclothModule {
	@Override
	public void run(String message) {
		System.out.println( "Hello from TestModule!\n\tRelaying message from the main-class: " + message );
	}
}
