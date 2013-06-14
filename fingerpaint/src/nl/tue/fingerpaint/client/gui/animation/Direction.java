package nl.tue.fingerpaint.client.gui.animation;

/**
 * Used in animation classes.
 * 
 * @author Group Fingerpaint
 */
public enum Direction implements HasUniqueBitIdentifier {
	/**
	 * Used to indicate that an animation should go to the top.
	 */
	TOP {
		@Override
		public int getId() {
			return 1;
		}
	},
	/**
	 * Used to indicate that an animation should go to the right.
	 */
	RIGHT {
		@Override
		public int getId() {
			return 2;
		}
	},
	/**
	 * Used to indicate that an animation should go to the bottom.
	 */
	BOTTOM {
		@Override
		public int getId() {
			return 4;
		}
	},
	/**
	 * Used to indicate that an animation should go to the left.
	 */
	LEFT {
		@Override
		public int getId() {
			return 8;
		}
	};
}
