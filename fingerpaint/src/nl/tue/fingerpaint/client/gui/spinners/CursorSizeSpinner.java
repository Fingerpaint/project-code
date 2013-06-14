package nl.tue.fingerpaint.client.gui.spinners;

import nl.tue.fingerpaint.client.model.ApplicationState;

/**
 * Numberspinner that can be used to change the size of the drawing tool.
 * 
 * @author Group Fingerpaint
 */
public class CursorSizeSpinner extends NumberSpinner implements
		NumberSpinnerListener {
	/** The default value of this spinner. */
	protected static final double DEFAULT_VALUE = 3.0;
	/** The rate of this spinner. */
	protected static final double DEFAULT_RATE = 1.0;
	/** The minimum value of this spinner. */
	protected static final double DEFAULT_MIN = 1.0;
	/** The maximum value of this spinner. */
	protected static final double DEFAULT_MAX = 50.0;

	/** Reference to the model. Used to change the size of the drawing tool. */
	protected ApplicationState as;

	/**
	 * Creates a new numberspinner that can be used to change the size of the
	 * drawing tool.
	 * 
	 * @param appState
	 *            Reference to the model, used to change the drawing tool size.
	 */
	public CursorSizeSpinner(ApplicationState appState) {
		super(DEFAULT_VALUE, DEFAULT_RATE, DEFAULT_MIN, DEFAULT_MAX, true);
		this.as = appState;
		setSpinnerListener(this);
		ensureDebugId("cursorSizeSpinner");
	}

	/**
	 * Sets the size of the drawing tool to {@code value}.
	 * 
	 * @param value The desired value that should be set.
	 */
	@Override
	public void onValueChange(double value) {
		as.getGeometry().setDrawingToolSize((int) value);
	}

}
