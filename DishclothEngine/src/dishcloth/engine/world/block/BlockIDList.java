package dishcloth.engine.world.block;

import dishcloth.api.abstractionlayer.world.block.IBlockID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * BlockIDList.java
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <p>
 * Stores BlockIDs for BlockIDHandler, and acts as a helper with internal String-to-short-to-BlockID -conversions
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Created by ASDSausage on 24.6.2015
 */

final class BlockIDList {

	private List<IBlockID> container;
	// <String, String, Integer> --> <Mod, IDString, Index in internal list>
	private HashMap<String, HashMap<String, Integer>> idStrings;
	// <Short, Integer> --> <ID, Index in internal list>
	private HashMap<Short, Integer> IDs;

	protected BlockIDList() {
		this.container = new ArrayList<>();
		this.idStrings = new HashMap<>();
		this.IDs = new HashMap<>();
	}

	protected boolean contains(Object o) {
		return container.contains( o );
	}

	protected void clear() {
		container.clear();
		IDs.clear();
		idStrings.clear();
	}

	protected IBlockID get(short id) {
		return (IDs.containsKey( id ) ? container.get( IDs.get( id ) ) : null);
	}

	protected IBlockID get(String mod, String id) {
		return (idStrings.containsKey( mod ) && idStrings.get( mod ).containsKey( id )
				? container.get( idStrings.get( mod ).get( id ) )
				: null);
	}

	protected boolean add(IBlockID blockID) {
		if (!idStrings.containsKey( blockID.getMod() )) {
			idStrings.put( blockID.getMod(), new HashMap<>() );
		} else {
			if (this.contains( blockID )) {
				return false;
			}
		}

		if (container.add( blockID )) {

			idStrings.get( blockID.getMod() ).put( blockID.getIDString(), container.size() - 1 );
			IDs.put( blockID.getID(), container.size() - 1 );
			return true;
		}

		return false;
	}

	protected List<IBlockID> asList() {
		return new ArrayList<>( this.container );
	}
}
