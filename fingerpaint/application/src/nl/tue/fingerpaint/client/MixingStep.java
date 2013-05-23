package nl.tue.fingerpaint.client;

import java.io.Serializable;


/**
 * MixingStep is a class that stores information for an individual mixing step of a mixing protocol
 * 
 * @author Roel van Happen
 *
 */
public class MixingStep implements Serializable {

	/**
	 * Auto-generated UID for the serialisation.
	 */
	private static final long serialVersionUID = -2790744362852192908L;
	/**the minimum step size, all step sizes should be a multiple of this*/
	public static final double STEP_UNIT = 0.25;
	/**lowest allowed step size*/
	public static final double STEP_MIN = 0.25;
	/**highest allowed step size*/
	public static final double STEP_MAX = 100;
	/**default step size*/
	public static final double STEP_DEFAULT = 1;
	
	/**nr of times stepUnit time is applied in the mixing step*/
	private int nrUnits;
	/**direction the direction of the wall movement, true is clockwise, false is counterclockwise*/
	private boolean direction;
	/**the wall that is moved, true for the top wall, false for the bottom wall*/
	private boolean wall;
	
	/**
	 * adds a single mixing step to the end of the current program
	 * 
	 * @param stepSize The stepSize of the wall movement, should be divisible by 0.25
	 * @param direction the direction of the wall movement, true is clockwise, false counterclockwise
	 * @param wall the wall that is moved, true for the top wall, false for the bottom wall
	 */
	public MixingStep(double stepSize, boolean direction, boolean wall){
		setStepSize(stepSize);
		setDirection(direction);
		setWall(wall);
	}
	
	/**
	 * Empty constructor, for serialisation purposes.
	 */
	private MixingStep(){}
	
	/**
	 * 
	 * @return result = 0.25*x with x an integer 
	 */
	double getStepSize(){
		return nrUnits*STEP_UNIT;
	}
	
	/**
	 * 
	 * @return true if the wall is moving clockwise, false otherwise
	 */
	boolean movesForward(){
		return direction;
	}
	
	/**
	 * 
	 * @return true if the top wall moves, false otherwise
	 */
	boolean isTopWall(){
		return wall;
	}
	
	/**
	 * 
	 * @param stepSize the time the mixing step is executed for
	 * @pre stepSize = 0.25*x with x an integer
	 * 
	 * If the precondition does not not hold, stepSize will be rounded to produce an integer x
	 */
	void setStepSize(double stepSize){
		nrUnits = (int)Math.round(stepSize/0.25);
	}
	
	/**
	 * 
	 * @param direction the direction the wall moves in, true if clockwise, false otherwise
	 */
	void setDirection(boolean direction){
		this.direction = direction;
	}
	
	/**
	 * 
	 * @param wall The wall that moves this mixing step, true for the top wall, false for the bottom wall
	 */
	void setWall(boolean wall){
		this.wall = wall;
	}
	
}
