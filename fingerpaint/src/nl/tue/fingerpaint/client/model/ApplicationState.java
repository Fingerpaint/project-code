package nl.tue.fingerpaint.client.model;

import nl.tue.fingerpaint.client.gui.GuiState;
import nl.tue.fingerpaint.shared.model.MixingProtocol;
import nl.tue.fingerpaint.shared.model.MixingStep;

/**
 * The {@code ApplicationState} is a class that can be and is used to keep track
 * of all relevant parameters in the application. Like {@link GuiState}, it is
 * some sort of manager, or central registry, for easy tracking of in this case
 * parameters chosen by the user. This includes the geometry and mixer that were
 * chosen, the protocol, step size, et cetera.
 * 
 * @author Group Fingerpaint
 */
public class ApplicationState {

	/** The chosen geometry. */
	private String geoChoice = null;
	/** The chosen matrix. */
	private String mixChoice = null;
	/** the current mixing protocol */
	private MixingProtocol protocol = new MixingProtocol();
	/** Geometry to draw on */
	private Geometry geom;
	/** The segregation array belonging to this state */
	private double[] segregation;
	/** Stores the current value for the Step size spinner. */
	private double stepsize;

	/**
	 * Stores the initial distribution, and not the current distribution as
	 * shown on the canvas, once set.
	 */
	private int[] initialDistribution = null;

	/**
	 * The number of times (#steps) that the defined protocol will be applied.
	 * Initially set to 0, to indicate that the spinner has not been loaded yet.
	 */
	private int nrSteps = 0;

	// --Getters and Setters------------------------------------------------
	/**
	 * Returns the current value of number of steps.
	 * 
	 * @return The current value of number of steps.
	 */
	public int getNrSteps() {
		return nrSteps;
	}

	/**
	 * Sets the value for the number of steps. It accepts a double, as the
	 * (default)value of the numberspinner is a double; it can immediately be
	 * converted to an integer, as the numberspinner for this variable
	 * guarantees that correct rounding has been performed, when this method is
	 * called.
	 * 
	 * @param steps
	 *            The new value for number of steps.
	 * 
	 *            <pre>
	 * {@code steps} is valid, according to the settings for the numberspinner in class Fingerpaint.
	 * @post The current number of steps is set to @param{nrSteps}.
	 */
	public void setNrSteps(double steps) {
		nrSteps = (int) steps;
	}

	/**
	 * Returns the long name of the chosen geometry.
	 * 
	 * @return the long name of the chosen geometry
	 */
	public String getGeometryChoice() {
		return geoChoice;
	}

	/**
	 * Change the chosen geometry. Note that it should be compatible with the
	 * chosen mixer!
	 * 
	 * @param g
	 *            The value to be set
	 */
	public void setGeometryChoice(String g) {
		geoChoice = g;
	}

	/**
	 * Change the chosen mixer. Note that it should be compatible with the
	 * chosen geometry!
	 * 
	 * @param m
	 *            The value to be set
	 */
	public void setMixerChoice(String m) {
		mixChoice = m;
	}

	/**
	 * Returns the name of the chosen mixer.
	 * 
	 * @return the name of the chosen mixer
	 */
	public String getMixerChoice() {
		return mixChoice;
	}

	/**
	 * Returns the mixing protocol
	 * 
	 * @return the current mixing protocol
	 */
	public MixingProtocol getProtocol() {
		return protocol;
	}

	/**
	 * Sets the current mixing protocol
	 * 
	 * @param mixingProtocol
	 *            , the new mixing protocol
	 * @throws NullPointerException
	 *             if mixingProtocol == null
	 */
	public void setProtocol(MixingProtocol mixingProtocol) {
		if (mixingProtocol == null) {
			throw new NullPointerException();
		}
		protocol = mixingProtocol;
	}

	/**
	 * Returns the segregation points
	 * 
	 * @return double array containing the segregation points
	 */
	public double[] getSegregation() {
		return segregation;
	}

	/**
	 * Sets the segregation points
	 * 
	 * @param segregation
	 *            The array containing the points to be set
	 */
	public void setSegregation(double[] segregation) {
		this.segregation = segregation;
	}

	/**
	 * Gives the current step size
	 * 
	 * @return The current step size
	 */
	public double getStepSize() {
		return stepsize;
	}

	/**
	 * Updates the current mixing step with a new value
	 * 
	 * @param value
	 *            the new StepSize for the current mixing step
	 */
	public void setStepSize(double value) {
		stepsize = value;
	}

	/**
	 * Gives the initial concentration distribution
	 * 
	 * @return The current initial concentration distribution
	 */
	public int[] getInitialDistribution() {
		return initialDistribution;
	}

	/**
	 * Sets the initial concentration distribution
	 * 
	 * @param distribution
	 *            The distribution to set
	 */
	public void setInitialDistribution(int[] distribution) {
		this.initialDistribution = distribution;
	}

	/**
	 * Gives the current geometry object
	 * 
	 * @return The geometry object
	 */
	public Geometry getGeometry() {
		return geom;
	}

	/**
	 * Sets the current geometry object
	 * 
	 * @param geometry
	 *            The geometry object to set
	 */
	public void setGeometry(Geometry geometry) {
		geom = geometry;
	}

	/**
	 * Add a step to the mixing protocol.
	 * 
	 * @param step
	 *            {@code Step} to be added.
	 */
	public void addMixingStep(MixingStep step) {
		protocol.addStep(step);
	}

	/**
	 * Draws the initial concentration distribution on the canvas
	 */
	public void drawDistribution() {
		geom.drawDistribution(initialDistribution);
	}
}
