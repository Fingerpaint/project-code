/**
 * 
 */
package nl.tue.fingerpaint.client;

/**
 * @author Group Fingerpaint
 *
 */
public class Movement {
	
	public enum HorizontalMovement {
		LEFT, RIGHT;
	}
	
	public enum VerticalMovement {
		UP, DOWN;
	}
	
	private HorizontalMovement horizontal = HorizontalMovement.LEFT;
	private VerticalMovement vertical = VerticalMovement.UP;
	
	public Movement() {
		
	}
	
	public Movement(HorizontalMovement horizontal, VerticalMovement vertical) {
		this.horizontal = horizontal;
		this.vertical = vertical;
	}

	public HorizontalMovement getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(HorizontalMovement horizontal) {
		this.horizontal = horizontal;
	}

	public VerticalMovement getVertical() {
		return vertical;
	}

	public void setVertical(VerticalMovement vertical) {
		this.vertical = vertical;
	}
	
}
