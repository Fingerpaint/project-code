package nl.tue.fingerpaint.client;

import java.util.ArrayList;

/**
 * 
 * @author Roel van Happen
 * 
 * This class symbolises a mixing program consisting of a number of mixing steps
 *
 */
public class MixingProgram {

	//the current mixing program
	ArrayList<MixingStep> program = new ArrayList<MixingStep>();
	
	/**
	 * Constructs an empty program
	 */
	public MixingProgram(){
		
	}
	
	/**
	 * adds a single mixing step to the end of the current program
	 * 
	 * @param stepSize The stepSize of the wall movement, should be divisible by 0.25
	 * @param direction the direction of the wall movement, true is clockwise, false counterclockwise
	 * @param wall the wall that is moved, true for the top wall, false for the bottom wall
	 */
	void addStep(double stepSize, boolean direction, boolean wall){
		MixingStep nextStep = new MixingStep(stepSize, direction, wall);
		program.add(nextStep);
	}
	
	/**
	 * 
	 * @param index the index of the step to be removed
	 * @pre 0 <= index < getProgramSize()
	 */
	void removeStep(int index){
		if(index >=0){
			if(index < program.size()){
				program.remove(index);
			}
		}
	}
	
	/**
	 * 
	 * @return the size of the program; i.e. the number of steps in it
	 */
	int getProgramSize(){
		return program.size();
	}
	
}
