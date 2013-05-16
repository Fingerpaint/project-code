/**
 * 
 */
package nl.tue.fingerpaint.client;

/**
 * @author Group Fingerpaint
 *
 */
public class Step {
		
	/**
	 * Indicates how long this step should be executed, in terms of D.
	 * It should be a combination of 4, 2, 1, 0.5 and 0.25.
	 */
	private int duration; // TODO: Do we check for valid input here or when setting the stepsize or both?
	/**
	 * Indicates the type of movement.
	 */
	private Movement movement;
	
	public Step() {
	
	}
	
	public Step(int duration, Movement movement) {
		this.duration = duration;
		this.movement = movement;
	}

	/**
	 * Get the duration for this step of the mixing protocol.
	 * @return the number of time units this step should be executed.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Set the duration for this step of the mixing protocol. Input is *not* checked for validity.
	 * @param duration The number of time units this step should be executed.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Get the movement for this Step.
	 * @return the movement for this Step.
	 */
	public Movement getMovement() {
		return movement;
	}

	/**
	 * Set the movement for this Step.
	 * @param movement The movement for this Step.
	 */
	public void setMovement(Movement movement) {
		this.movement = movement;
	}

}
