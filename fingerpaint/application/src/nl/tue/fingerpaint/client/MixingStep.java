package nl.tue.fingerpaint.client;

import java.io.Serializable;

import org.jsonmaker.gwt.client.Jsonizer;

/**
 * MixingStep is a class that stores information for an individual mixing step of a mixing protocol
 * 
 * @author Group Fingerpaint
 *
 */
public class MixingStep implements Serializable{
	
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
	
	public MixingStep() {
		this(1.0, true, true);
	}
	/**
	 * 
	 * @return result = 0.25*x with x an integer 
	 */
	public double getStepSize(){
		return nrUnits*STEP_UNIT;
	}
	
	/**
	 * 
	 * @return true if the wall is moving clockwise, false otherwise
	 */
	public boolean movesForward(){
		return direction;
	}
	
	/**
	 * 
	 * @return true if the top wall moves, false otherwise
	 */
	public boolean isTopWall(){
		return wall;
	}
	
	/**
	 * 
	 * @param stepSize the time the mixing step is executed for
	 * @pre stepSize = 0.25*x with x an integer
	 * 
	 * If the precondition does not not hold, stepSize will be rounded to produce an integer x
	 */
	public void setStepSize(double stepSize){
		nrUnits = (int)Math.round(stepSize/0.25);
	}
	
	/**
	 * 
	 * @param direction the direction the wall moves in, true if clockwise, false otherwise
	 */
	public void setDirection(boolean direction){
		this.direction = direction;
	}
	
	/**
	 * 
	 * @param wall The wall that moves this mixing step, true for the top wall, false for the bottom wall
	 */
	public void setWall(boolean wall){
		this.wall = wall;
	}
	
	
	
	public static double getStepUnit() {
		return STEP_UNIT;
	}

	public static double getStepMin() {
		return STEP_MIN;
	}

	public static double getStepMax() {
		return STEP_MAX;
	}

	public static double getStepDefault() {
		return STEP_DEFAULT;
	}

	public int getNrUnits() {
		return nrUnits;
	}

	public boolean getDirection() {
		return direction;
	}

	public boolean getWall() {
		return wall;
	}
	
	/**
	 * TODO: Currently hardcoded needs to be dynamic
	 * 
	 * @return The name of the mixing step
	 */
	public String getName() {
		StringBuilder builder = new StringBuilder();
		if (isTopWall()) {
			builder.append('T');
		} else {
			builder.append('B');
		}
		if (movesForward()) {
			builder.append('R');
		} else {
			builder.append('L');
		}
		return builder.toString();
	}

	public interface MixingStepJsonizer extends Jsonizer { }
	
}