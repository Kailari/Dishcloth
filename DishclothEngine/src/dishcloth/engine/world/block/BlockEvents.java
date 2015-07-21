package dishcloth.engine.world.block;

import dishcloth.engine.events.IEvent;

/**
 * BlockEvents.java
 * <p>
 * For easy access to all block-events
 * <p>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * <br>
 * Created by ASDSausage on 20.7.2015
 */

public class BlockEvents {

	private BlockEvents() {}

	/**
	 * Event that gets fired during block registration just after the block has
	 * been registered and it has been assigned block- and frameIDs
	 */
	public static class OnBlockRegistrationEvent implements IEvent {
		private final BlockID registeredBlockID;
		private final ABlock registeredBlock;

		public OnBlockRegistrationEvent(BlockID registeredBlockID, ABlock registeredBlock) {
			this.registeredBlockID = registeredBlockID;
			this.registeredBlock = registeredBlock;
		}

		/**
		 * @return ID of the block that just got registered.
		 */
		public BlockID getRegisteredBlockID() {
			return registeredBlockID;
		}

		/**
		 * @return Block that just got registered.
		 */
		public ABlock getRegisteredBlock() {
			return registeredBlock;
		}
	}
}
