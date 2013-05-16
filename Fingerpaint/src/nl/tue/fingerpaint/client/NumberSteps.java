package nl.tue.fingerpaint.client;

/**
 * A class to represent the number of times that the defined protocol will be applied.
 * 
 * @author Femke Jansen
 *
 */
public class NumberSteps 
{
	/*
	 * The number of times (#steps) that the defined protocol will be applied. 
	 * Initially set to 1, so the value is valid from the beginning.
	 */
	private int nrSteps = 1; // #steps

	/**
	 * Returns the current value of number of steps.
	 * 
	 * @return	The current value of number of steps.
	 */
	public int getNrSteps()
	{
		return nrSteps;
	}
	
	/**
	 * Sets the value for the number of steps.
	 * 
	 * @param nrSteps	The new value for number of steps.
	 * 
	 * <pre>	nrSteps >= 1
	 * @post	The current number of steps is set to @param{nrSteps}.
	 */
	public void setNrSteps(int nrSteps)
	{
		this.nrSteps = nrSteps;
	}
	
}
