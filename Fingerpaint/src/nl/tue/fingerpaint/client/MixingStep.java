package nl.tue.fingerpaint.client;


/**
 * 
 * @author Roel van Happen
 *
 * MixingStep is a class that stores information for an individual mixing step of a mixing protocol
 *
 */
public class MixingStep {

	static final double stepUnit = 0.25;
	private int nrUnits = 1; //nr of times stepUnit time is applied in the mixing step
	
	/**
	 * 
	 * @return result = 0.25*x with x an integer 
	 */
	double getStepSize(){
		return nrUnits*stepUnit;
	}
	
	/**
	 * 
	 * @param stepSize 
	 * @pre stepSize = 0.25*x with x an integer
	 * 
	 * If the precondition does not not hold, stepSize will be rounded to produce an integer x
	 */
	void setStepSize(double stepSize){
		nrUnits = (int)Math.round(stepSize/0.25);
	}
	
}
