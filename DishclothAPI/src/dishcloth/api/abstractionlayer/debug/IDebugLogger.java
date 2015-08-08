package dishcloth.api.abstractionlayer.debug;

/**
 * <b>IDebugLogger</b>
 * <p>
 * Debug logger abstraction
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 6.8.2015 at 14:09
 */
public interface IDebugLogger {

	void log(String message, Object context);

	void logNote(String message, Object context);

	void logOK(String message, Object context);

	void logWarn(String message, Object context);

	void logErr(String message, Object context);

	void logException(Exception e, Object context);
}
