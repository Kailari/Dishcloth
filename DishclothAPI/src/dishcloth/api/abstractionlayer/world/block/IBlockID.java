package dishcloth.api.abstractionlayer.world.block;

/**
 * <b>IBlockID</b>
 * <p>
 * TODO: Description
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *<br>
 * Created by ASDSausage on 7.8.2015 at 10:42
 */
public interface IBlockID {

	String getMod();

	String getIDString();

	short getID();

	short getFallbackID();
}
