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
	 * Initially set to 0, to indicate that the spinner has not been loaded
	 * yet.
	 */
	private int nrSteps = 0; // #steps
	private double stepsize;
	
	/**
	 * Returns the current value of number of steps.
	 * 
	 * @return The current value of number of steps.
	 */
	public int getNrSteps() {
		return nrSteps;
	}

	/**
	 * Sets the value for the number of steps.
	 * It accepts a double, as the (default)value of the numberspinner
	 * is a double; it can immediately be converted to an integer, as
	 * the numberspinner for this variable guarantees that correct rounding has been
	 * performed, when this method is called.
	 * 
	 * @param nrSteps
	 *            The new value for number of steps.
	 * 
	 *            <pre>
	 * {@param steps} is valid, according to the settings for the numberspinner in class Fingerpaint.
	 * @post The current number of steps is set to @param{nrSteps}.
	 */
	public void setNrSteps(double steps) {
		nrSteps = (int)steps;
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
	 * @return the currently selected mixing step, can be null if no step is being edited
	 */
	public MixingStep getCurrentStep(){
		return currMixingStep;
	}
	
	/**
	 * Updates the current mixing step with a new value
	 * 
	 * @param value the new StepSize for the current mixing step
	 */
	public void editStepSize(double value){
		stepsize = value;
	}
	
	/**
	 * sets the current mixing step to the step newStep, can be null to indicate no step is edited now
	 */
	public void setCurrentStep(MixingStep newStep){
		currMixingStep = newStep;
	}
	
	/**
	 * adds the current mixing step to the end of the mixing protocol, then clears the current mixing step (makes it null)
	 */
	public void addCurrentStep(){
		protocol.addStep(currMixingStep);
		currMixingStep = null;
	}
	
	//the current mixing protocol
	private MixingProtocol protocol = new MixingProtocol();
	//the current mixing step
	private MixingStep currMixingStep;
	
	/**
	 * Add a step to the mixing protocol.
	 * @param step {@code Step} to be added.
	 */
	public void addMixingStep(MixingStep step) {
		protocol.addStep(step);
	}

	public double getStepSize() {
		return stepsize;
	}
}
