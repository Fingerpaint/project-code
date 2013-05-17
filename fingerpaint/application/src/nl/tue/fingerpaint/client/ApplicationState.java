package nl.tue.fingerpaint.client;

/**
 * Class that keeps track of the Geometry and Mixer the user has selected. Used
 * by the cellBrowser widget in Fingerpaint.java to store chosen variables.
 * 
 * @author Group Fingerpaint
 */
public class ApplicationState {

	private GeometryNames geoChoice = null;
	private Mixer mixChoice = null;
	
	/*
	 * The number of times (#steps) that the defined protocol will be applied.
	 * Initially set to 0.0, to indicate that the spinner has not been loaded
	 * yet.
	 */
	private double nrSteps = 0.0; // #steps
	
	/**
	 * Returns the current value of number of steps.
	 * 
	 * @return The current value of number of steps.
	 */
	public double getNrSteps() {
		return nrSteps;
	}

	/**
	 * Sets the value for the number of steps.
	 * 
	 * @param nrSteps
	 *            The new value for number of steps.
	 * 
	 *            <pre>
	 * {@param steps} is valid, according to the settings for the NumberSpinner in class Fingerpaint.
	 * @post The current number of steps is set to @param{nrSteps}.
	 */
	public void setNrSteps(double steps) {
		nrSteps = steps;
	}

	/**
	 * Sets {@code geoChoice} to the given geometry {@code g}
	 * 
	 * @param g
	 *            The value to be set
	 */
	public void setGeometry(GeometryNames g) {
		geoChoice = g;
	}

	/**
	 * Sets {@code mixChoice} to the given Mixer {@code m}
	 * 
	 * @param m
	 *            The value to be set
	 */
	public void setMixer(Mixer m) {
		mixChoice = m;
	}

	/**
	 * Returns the value of the chosen Geometry
	 * 
	 * @return The value of {@code geoChoice} 
	 */
	public GeometryNames getGeometryChoice() {
		return geoChoice;
	}

	/**
	 * Returns the value of the chosen Mixer
	 * 
	 * @return The value of {@code mixChoice} 
	 */
	public Mixer getMixerChoice() {
		return mixChoice;
	}
	
	public MixingProtocol getProtocol(){
		return protocol;
	}
	
	/**
	 * sets the current mixing protocol
	 * 
	 * @param mixingProtocol, the new mixing protocol
	 * @throws NullPointerException if mixingProtocol == null
	 */
	public void setProtocol(MixingProtocol mixingProtocol){
		if(mixingProtocol == null){
			throw new NullPointerException();
		}
		protocol = mixingProtocol;
	}
	
	/**
	 * 
	 * @return the index of the currently selected mixing step
	 */
	public int getCurrentStepIndex(){
		return currMixingStep;
	}
	
	/**
	 * 
	 * @return the currently selected mixing step
	 */
	public MixingStep getCurrentStep(){
		return protocol.getStep(currMixingStep);
	}
	
	//the current mixing protocol
	private MixingProtocol protocol = new MixingProtocol();
	//the index of the current mixing step
	private int currMixingStep;
	
	// TODO: Benjamin's code.
	
	/**
	 * Stores the mixing protocol for the selected geometry.
	 */
	private Protocol bProtocol = new Protocol();
	
	/**
	 * Add a step to the protocol.
	 * @param step The {@code Step} to be added to the protocol.
	 */
	public void addStep(Step step) {
		bProtocol.addStep(step);
		
	}
}
