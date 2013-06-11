package nl.tue.fingerpaint.client.gui.spinners;

import nl.tue.fingerpaint.client.model.ApplicationState;

/**
 * Numberspinner that can be used to change the size of the drawing tool.
 * 
 * @author Group Fingerpaint
 */
public class NrStepsSpinner extends NumberSpinner implements
		NumberSpinnerListener {
	/** The default value of this spinner. */
	public static final double DEFAULT_VALUE = 1.0;
	/** The rate of this spinner. */
	public static final double DEFAULT_RATE = 1.0;
	/** The minimum value of this spinner. */
	public static final double DEFAULT_MIN = 1.0;
	/** The maximum value of this spinner. */
	public static final double DEFAULT_MAX = 50.0;

	/**
	 * Reference to the model. Used to change how many times the mixing protocol
	 * is executed
	 */
	protected ApplicationState as;

	/**
	 * Creates a new numberspinner that can be used to change how many times the
	 * mixing protocol is executed (#steps).
	 * 
	 * @param appState
	 *            Reference to the model, used to change the #steps-parameter.
	 */
	public NrStepsSpinner(ApplicationState appState) {
		super(DEFAULT_VALUE, DEFAULT_RATE, DEFAULT_MIN, DEFAULT_MAX, true);
		this.as = appState;
		as.setNrSteps(DEFAULT_VALUE);
		setSpinnerListener(this);
		ensureDebugId("nrStepsSpinner");
	}

	/**
	 * Sets the value for this spinner.
	 * @param value The value that should be set.
	 */
	@Override
	public void onValueChange(double value) {
		as.setNrSteps(value);
	}

}
